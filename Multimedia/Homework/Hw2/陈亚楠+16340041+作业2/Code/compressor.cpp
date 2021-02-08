#include "compressor.h"

Compressor::Compressor() {
	static std::string bitsMask[10] = {"", "0", "00", "000", "0000", "00000", "000000", "0000000", "00000000"};
	shiftedBit = bitsMask;

	static int zzMask[64] = {
			  0,  1,  8, 16,  9,  2, 3,  10,
			  17, 24, 32, 25, 18, 11,  4,  5,
			  12, 19, 26, 33, 40, 48, 41, 34,
			  27, 20, 13,  6,  7, 14, 21, 28,
			  35, 42, 49, 56, 57, 50, 43, 36,
			  29, 22, 15, 23, 30, 37, 44, 51,
			  58, 59, 52, 45, 38, 31, 39, 46,
			  53, 60, 61, 54, 47, 55, 62, 63};
	zigZagMask = zzMask;

	//quantization
	luminance = (cv::Mat_<float>(8, 8) <<
			16, 11, 10, 16, 24, 40, 51, 61,
			12, 12, 14, 19, 26, 58, 60, 55,
			14, 13, 16, 24, 40, 57, 69, 56,
			14, 17, 22, 29, 51, 87, 80, 62,
			18, 22, 37, 56, 68, 109, 103, 77,
			24, 35, 55, 64, 81, 104, 113, 92,
			49, 64, 78, 87, 103, 121, 120, 101,
			72, 92, 95, 98, 112, 100, 103, 99);

	width = 0;
	height = 0;
	w2m8 = 0;
	h2m8 = 0;
	C = 1;//Compress rate - [1 Maximal - 8 Minimal]
	numberBitsShifted = 0;
}

void Compressor::setParamCompression(char* imageName){
	// Load image in gray scale
	grayImage = cv::imread( imageName, cv::IMREAD_GRAYSCALE );

	//Check if image exist
	if( !grayImage.data ){
		std::cout << "\033[1;31mCan't load the image. Please insert the image address.\033[0m\n" << std::endl;
		throw std::exception();
	}

	// Modify the image names to .compactado
	fileName.clear();
	fileName.append(imageName);
	fileName.erase(fileName.end()-4, fileName.end());
	fileName +=  ".compactado"; //sufix

	//Open output streams to .compactado
	outfile.open(fileName.c_str());

	// Transform the image resolutions to a resolution with size multiple of 8 by 8
	// Necessary to broke the image in 8 by 8 blocks in DCT step
	height = grayImage.size().height;
	width = grayImage.size().width;

	h2m8 = (height%8) ? (h2m8 = 8 - height%8) : 0;
	w2m8 = (width%8) ?  (w2m8 = 8 - width%8) : 0;

	if (h2m8 != 0){
		cv::Mat hBlock = grayImage(cv::Rect(0, height-h2m8, width, h2m8));
		cv::vconcat(grayImage, hBlock, grayImage);
		height += h2m8;
	}

	if (w2m8 != 0){
		cv::Mat wBlock = grayImage(cv::Rect(width-w2m8, 0, w2m8, height));
		cv::hconcat(grayImage, wBlock, grayImage);
		width += w2m8;
	}

	// Write the image's size in .compactado
	outfile << height << " " << width << std::endl;
	outfile << h2m8 << " " << w2m8 << std::endl;

}

// Forward transformation - DCT
void Compressor::forwardDCT(){

	dctImage = cv::Mat_<uchar>(height*C/8, width*C/8);

	for (int i = 0; i < height; i += 8){
		for (int j = 0; j < width; j += 8){
				// Get a block 8x8 for each position on image
				cv::Mat block8 = grayImage(cv::Rect(j, i, 8, 8));

				// Convert block from 8 bits to 64 bits
				block8.convertTo(block8, CV_32FC1);

				// DCT
				cv::dct(block8, block8);

				// Quantization step
				cv::divide(block8, luminance, block8);

				// Adding 128 to the block
				cv::add(block8, 128.0, block8);

				// Converting it back to unsigned char
				block8.convertTo(block8, CV_8UC1);

				// Copying the block back to the new dct image
				//block8.copyTo(dctImage(cv::Rect(j, i, 8, 8)));

				cv::Mat blockC = cv::Mat_<uchar>(C, C);

				for (int k = 0; k < (int)C*C; k++)
					blockC.at<uchar>(k) = block8.at<uchar>(zigZagMask[k]);
				//std::cout << "Copyto\n";
				blockC.copyTo(dctImage(cv::Rect(j*C/8, i*C/8, C, C)));


		}
	}
	dctImage.convertTo(dctImage, CV_8UC1);
}

void Compressor::inverseDCT(){
	grayImage = cv::Mat_<uchar>(height, width);

	for (int i = 0; i < height*C/8; i+=C){
		for (int j = 0; j < width*C/8; j+=C){
			// Get a block 8x8 for each position on image
			cv::Mat block = dctImage(cv::Rect(j, i, C, C));

			// Convert block from 8 bits to 64 bits
			block.convertTo(block, CV_32FC1);

			// Subtracting the block by 128
			cv::subtract(block, 128, block);

			cv::Mat block8 = cv::Mat::zeros(8, 8, CV_32FC1);
			for (int k = 0; k < (int)C*C; k++)
					block8.at<float>(zigZagMask[k]) = block.at<float>(k);

			// Quantization step
			cv::multiply(block8, luminance, block8);

			// Inverse DCT
			cv::idct(block8, block8);

			// Converting it back to unsigned char
			block8.convertTo(block8, CV_8UC1);

			// Copying the block back to the new dct image
			//std::cout << "Copyto\n";
			block8.copyTo(grayImage(cv::Rect(8*j/C, 8*i/C, 8, 8)));
		}
	}

}

// Display all images
void Compressor::displayImage(std::string imageName, cv::Mat image){
	// Display
	cv::namedWindow(imageName, CV_WINDOW_AUTOSIZE );
	cv::imshow(imageName, image);
	cv::waitKey(0);
}

// Compute the histogram matrix for dct image in planes
void Compressor::searchFrequenceTable(){
	int histSize = 256; // Establish the number of bins

	float range[] = {0, 256}; // Set the ranges
	const float* histRange = {range};

	bool uniform = true;
	bool accumulate = false;

	cv::Mat histogram;//Histogram for DCT Image

	cv::calcHist( &dctImage, 1, 0, cv::Mat(), histogram, 1, &histSize, &histRange, uniform, accumulate);

	std::vector<float> f(histogram.begin<float>(), histogram.end<float>());//Transform mat to vector

	h.setFrequenceTable(f);
}

//Compressor step
void Compressor::compress(char* imageName){
	//Just for information
	std::cout << "\033[0;32mCompressing...\033[0m\n";

	std::cout << "\033[0;32mSetting Parameter...\033[0m\n";
	setParamCompression(imageName);

	std::cout << "\033[0;32mTransforming Image...\033[0m\n";
	forwardDCT();

	std::cout << "\033[0;32mSearching Frequency Table...\033[0m\n";
	searchFrequenceTable();

	std::cout << "\033[0;32mComputing Symbol Table...\033[0m\n";
	codeTable = h.getHuffmanCode();

	//Write the Frequency Table in the file
	std::cout << "\033[0;32mPrinting Frequency Table...\033[0m\n";
	for (unsigned short i = 0; i < codeTable.size(); i++){
		if (codeTable.at(i) == 0) continue;
		outfile << i  << " " << codeTable.at(i) << std::endl;
	}

	//transform image Mat to vector
	std::vector<int> inputFile;
 	inputFile.assign(dctImage.datastart, dctImage.dataend);

	//Codify image dct based on table of code from Huffman
	std::cout << "\033[0;32mEncoding Image with Huffman...\033[0m\n";
	codifiedImage = h.encode(inputFile);

	//Add 0's in the end of the file to complete a byte
	numberBitsShifted = 8 - codifiedImage.length()%8;
	codifiedImage += shiftedBit[numberBitsShifted];


	//End of the code table and the number of bits 0 shifted in the end of the file
	outfile << "#" << numberBitsShifted  << std::endl;

	std::cout << "\033[0;32mPrinting Image...\033[0m\n";
	size_t imSize = codifiedImage.size();
	for (size_t i = 0; i < imSize; i += 8){
		outfile << (uchar)std::strtol(codifiedImage.substr(i, 8).c_str(), 0, 2);
	}
	codifiedImage.clear();

	//Close the file .compactado
	outfile.close();

	//Just for information
	if (!codifiedImage.length())
		std::cout << "\033[0;32mCompressing Successful!\033[0m\n";
	else
		std::cout << "\033[0;31mCompressing Failed!\033[0m\n";

}

void Compressor::setParamDecompression(char* imageName){
	fileName.clear();
	fileName.append(imageName);

	if (fileName.substr(fileName.length()-11, fileName.length()) != ".compactado"){
		std::cout << "\033[0;31mCan't load the image. Please insert the image address.\033[0m\n" << std::endl;
		throw std::exception();
	}
	fileName.erase(fileName.length()-11, fileName.length());
	fileName += ".saida.png";

	infile.open(imageName);
	if (!infile.is_open()){
		std::cout << "\033[0;31mCan't load the image. Please insert the image address.\033[0m\n" << std::endl;
				throw std::exception();
	}

	std::string size;//Get the original size of the image
	infile >> size;
	height = std::atoi(size.c_str());
	infile >> size;
	width = std::atoi(size.c_str());

	infile >> size;
	h2m8 = std::atoi(size.c_str());
	infile >> size;
	w2m8 = std::atoi(size.c_str());
}

void Compressor::readCodeTable(){

	std::string ind, frequency;
	infile >> ind;

	while(ind[0] != '#'){
		infile >> frequency;
		h.setFrequenceTable(std::atoi(ind.c_str()), std::strtof(frequency.c_str(), 0));
		infile >> ind;
	}
	ind.erase(0,1);//remove '#'
	numberBitsShifted = std::atol(ind.c_str());
}

void Compressor::readCodifiedImage(){
	unsigned char c;
	unsigned char mask = 128;//1000 0000
	//codifiedImage.clear();

	int count = 0;

	infile >> std::noskipws;
	infile >> c;

	while(infile >> c){
		for (int i = 0; i < 8; i++){

			if ((c << i) & mask){
				codifiedImage.push_back('1');
			}
			else{
				codifiedImage.push_back('0');
			}
			count++;
		}
	}
	codifiedImage.erase(codifiedImage.length()-numberBitsShifted, numberBitsShifted);//remove the exceed bits
}

void Compressor::decompress(char* fileName){
	//Just for information
	std::cout << "\033[0;32mUncompressing...\033[0m\n";

	std::cout << "\033[0;32mSetting Parameters...\033[0m\n";
	setParamDecompression(fileName);

	std::cout << "\033[0;32mReading Symbol Table ...\033[0m\n";
	readCodeTable();

	std::cout << "\033[0;32mReading Codified Image ...\033[0m\n";
	readCodifiedImage();

	std::cout << "\033[0;32mDecoding Image with Huffman...\033[0m\n";
	std::vector<int> dctFile = h.decode(codifiedImage);

	dctImage = cv::Mat::zeros(height*C/8, width*C/8, CV_8UC1);

	std::cout << "\033[0;32mTransforming Image...\033[0m\n";
	for (int i = 0; i < height*C/8; i++){
		for (int j = 0; j < width*C/8; j++){
			dctImage.at<uchar>(i, j) = (uchar)dctFile.at(i*dctImage.step + j);
		}
	}
	std::cout << "\033[0;32mProcessing Image...\033[0m\n";
	inverseDCT();

	if (w2m8 != 0)
		grayImage = grayImage.colRange(0, width - w2m8);

	if (h2m8 != 0)
		grayImage = grayImage.rowRange(0, height - h2m8);

	cv::imwrite(this->fileName, grayImage);
	std::cout << "\033[0;32mDone!\033[0m\n";
}

Compressor::~Compressor() {}
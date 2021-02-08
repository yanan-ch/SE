#include "huffman.h"

Huffman::Huffman() {}

Huffman::~Huffman() {}

node Huffman::getHuffmanTree(){
	while(!nodeArray.empty()){
		node* rootNode = new node;
		node* leftNode = new node;
		node* rightNode = new node;

		*leftNode = nodeArray.top();
		nodeArray.pop();
		*rightNode = nodeArray.top();
		nodeArray.pop();

		rootNode->leftChild = leftNode;
		rootNode->rightChild = rightNode;
		rootNode->frequency = leftNode->frequency + rightNode->frequency;

		nodeArray.push(*rootNode);
		if(nodeArray.size() == 1){//only the root node exsits
			break;
		}
	}

	return nodeArray.top();
}

//BFS algorithm to search the symbol of the content in the tree
void Huffman::BFS(node* temproot, std::string s){
	node* root1 = new node;
	root1 = temproot;

	root1->code = s;

	if (root1 == NULL){}
	else if(root1->leftChild == NULL && root1->rightChild == NULL){
		short i = root1->content;
		codeTable.at(i) = root1->frequency;
		symbolTable.at(i) = root1->code;
	}
	else{
		root1->leftChild->code = s.append("0");
		s.erase(s.end()-1);
		root1->rightChild->code = s.append("1");
		s.erase(s.end()-1);

		BFS(root1->leftChild, s.append("0"));
		s.erase(s.end()-1);
		BFS(root1->rightChild, s.append("1"));
		s.erase(s.end()-1);
	}
}

//Compute and return the table of symbols
std::vector<float> Huffman::getHuffmanCode(){

	node root = getHuffmanTree();//construct the huffman tree

	codeTable.resize(UCHAR_MAX + 1);//Code table with 256 bins
	symbolTable.resize(UCHAR_MAX + 1);//Code table with 256 bins

	BFS(&root,"");//Search tree-basead code with BFS algorithm

	return codeTable;//return table of symbols
}

//Insert the frequency of the symbols into a priority queue
void Huffman::setFrequenceTable(std::vector<float> f){

	for (unsigned i = 0; i < f.size(); i++){
		setFrequenceTable(i, f[i]);
	}
}

void Huffman::setFrequenceTable(int ind, float frequency){

	if (frequency <= 0) return;

	node temp;
	temp.frequency = frequency;
	temp.content = ind;
	temp.leftChild = NULL;
	temp.rightChild = NULL;
	nodeArray.push(temp);
}

std::string Huffman::encode(std::vector<int> e){
	std::string codifiedImage = "";

	for(unsigned i = 0; i < e.size() ; i++){
			if (symbolTable.at(e.at(i)) == ""){
				std::cout << "\n\033[1;31mError: Code don't exist in CodeTable.\033[0m\n" << std::endl;
			}
			codifiedImage += symbolTable.at(e.at(i));
	}
	return codifiedImage;
}

std::vector<int> Huffman::decode(std::string d){

	node root = getHuffmanTree();

	searchContent(&root, d);

	return image;
}

void Huffman::searchContent(node* root, std::string im){
	node* n = new node;
	n = root;

	size_t imSize = im.length();
	image.clear();

	for (size_t i = 0; i <= imSize; i++){
		if (n->leftChild == NULL && n->rightChild == NULL){//leaf
			image.push_back(n->content);
			n = root;
		}

		n = (im[i] == '1') ? n->rightChild : n->leftChild;

	}
	im.clear();
}
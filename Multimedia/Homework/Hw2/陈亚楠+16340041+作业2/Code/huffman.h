#ifndef SRC_HUFFMAN_H_
#define SRC_HUFFMAN_H_

# include<iostream>
# include<vector>
# include<queue>
# include<climits>
# include<cstdlib>
# include<algorithm>

struct node{
	node* leftChild;
	node* rightChild;
	int frequency;
	int content;
	std::string code;

	bool operator<(const node &a) const { return (frequency > a.frequency);}
};

class Huffman {
	std::priority_queue<node> nodeArray;
	std::vector<float> codeTable;
	std::vector<std::string> symbolTable;
	std::vector<int> image;

public:
	Huffman();
	node getHuffmanTree();
	void BFS(node* temproot, std::string s);
	std::vector<float> getHuffmanCode();
	void setFrequenceTable(std::vector<float> f);
	void setFrequenceTable(int ind, float frequency);
	std::string encode(std::vector<int> e);
	std::vector<int> decode(std::string d);
	void searchContent(node* root, std::string im);
	virtual ~Huffman();
};

#endif
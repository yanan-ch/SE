#include <iostream>

#include "compressor.h"

int main(int argc, char** argv) {

	Compressor c;
	c.compress(argv[1]);

	return 0;
}
#include<iostream>

void main() {

	int arrayKu[10]{};

	for (int a = 0; a < 10; a++) {
		arrayKu[a] = a;
	}
	std::cout << arrayKu[5] << std::endl;
}
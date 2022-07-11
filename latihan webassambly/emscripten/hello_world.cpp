#include <iostream>
#include <emscripten.h>

int main()
{
  std::cout << "Hello World\n";
  return 0;
}

EMSCRIPTEN_KEEPALIVE
void myFunction()
{
  std::cout << "TES" << std::endl;
}
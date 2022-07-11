#include <stdio.h>
#include "emscripten.h"

int main()
{
  printf("Hello World\n");
  return 0;
}

EMSCRIPTEN_KEEPALIVE
int pangkat(int angka)
{
  return angka * angka;
}

EMSCRIPTEN_KEEPALIVE
void sayHello()
{
  printf("Hello Sam");
}
#include <iostream>
#include <emscripten.h>

int main()
{
  std::cout << "Hello World\n";
}

extern "C"
{
  int pangkat(int angka, int pangkat)
  {
    int hasil = angka;
    for (int i = 0; i < pangkat; i++)
    {
      hasil *= angka;
    }
    return hasil;
  }
}

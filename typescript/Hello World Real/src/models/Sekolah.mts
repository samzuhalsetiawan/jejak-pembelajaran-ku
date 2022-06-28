export default abstract class Sekolah {
  abstract sd: string;
  getSMP() {
    return "SMP1";
  }
  abstract getSMA(namaSma: string): string;
}
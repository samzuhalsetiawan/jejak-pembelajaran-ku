var s = "";

for (b = 1; b <= 8; b++){
	if (b % 2 == 0){
		for (a = 1; a < 8; a++){
			if (a % 2 == 0){
				s += "#";
			}else{
				s += " ";
			}
		}
		s += "\n";
	}else{
		for (a = 1; a < 8; a++){
			if (a % 2 == 0){
				s += " ";
			}else{
				s += "#";
			}
		}
		s += "\n";
	}
}
console.log(s);
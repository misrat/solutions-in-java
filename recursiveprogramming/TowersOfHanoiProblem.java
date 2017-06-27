package recursiveprogramming;

public class TowersOfHanoiProblem {
	
	public static void main(String[] args) {
		TowersOfHanoi(4, "TowerA", "TowerB", "TowerC");
	}

	// move the disks from src to dest via buf
	public static void TowersOfHanoi(int n, String src, String dest, String buf) {
		
		if (n == 1) {
			System.out.println("Moving Disk from " + src + " to " + dest);
		} else {
			TowersOfHanoi(n - 1, src, buf, dest);
			System.out.println("Moving the Disk " + n + " from " + src +" to "+ dest);
			TowersOfHanoi(n - 1, buf, dest, src);
		}
	}
}

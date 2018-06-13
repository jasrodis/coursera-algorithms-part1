public class PercolationStats{
    
    private int num_of_experim;
    private double[] outcomes;
    
    public PercolationStats(int N, int T) {
        if (N <= 0) throw new IllegalArgumentException("Non-positive size of grid.");
        if (T <= 0) throw new IllegalArgumentException("Non-positive number of experiments.");
        num_of_experim = T;
        outcomes = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation grid = new Percolation(N);
            while (!grid.percolates()){
                int x = StdRandom.uniform(N)+1;
                int y = StdRandom.uniform(N)+1;
                if (!grid.isOpen(x, y)) {
                    grid.open(x, y);
                    outcomes[i]++;
                }
            }
            outcomes[i] /= N*N;
        }
    }
    
    public double mean() {
        return StdStats.mean(outcomes);
    }
    
    public double stddev() {
        return StdStats.stddev(outcomes);
    }
    
    public double confidenceLo() {
        return this.mean() - 1.96 * this.stddev() / Math.sqrt(num_of_experim);
    }
    
    public double confidenceHi() {
        return this.mean() + 1.96 * this.stddev() / Math.sqrt(num_of_experim);
    }
    
    public static void main(String[] args){
        System.out.println("Input size of grid:");
        int N = StdIn.readInt();
        System.out.println("Input number of experiments:");
        int T = StdIn.readInt();
        PercolationStats percstats = new PercolationStats(N, T);
        System.out.println("mean = " + percstats.mean());
        System.out.println("stddev = " + percstats.stddev());
        System.out.println("95% confidence interval = [" + percstats.confidenceLo() + ", " + percstats.confidenceHi() + "]" );
    }
}

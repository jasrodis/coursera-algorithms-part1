## Percolation:

Model: We model a percolation system using an N-by-N grid of sites. Each site is either open or blocked. A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites. We say the system percolates if there is a full site in the bottom row. In other words, a system percolates if we fill all open sites connected to the top row and that process fills some open site on the bottom row. (For the insulating/metallic materials example, the open sites correspond to metallic materials, so that a system that percolates has a metallic path from top to bottom, with full sites conducting. For the porous substance example, the open sites correspond to empty space through which water might flow, so that a system that percolates lets water fill open sites, flowing from top to bottom.)

## API:
``
public class Percolation {
   public Percolation(int N)               // create N-by-N grid, with all sites blocked
   public void open(int i, int j)          // open site (row i, column j) if it is not open already
   public boolean isOpen(int i, int j)     // is site (row i, column j) open?
   public boolean isFull(int i, int j)     // is site (row i, column j) full?
   public boolean percolates()             // does the system percolate?

   public static void main(String[] args)  // test client (optional)
}
``



## Percolation Stats:

Model: To estimate the percolation threshold, consider the following computational experiment:

Initialize all sites to be blocked.
Repeat the following until the system percolates:
Choose a site (row i, column j) uniformly at random among all blocked sites.
Open the site (row i, column j).
The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.
For example, if sites are opened in a 20-by-20 lattice, then our estimate of the percolation threshold is 204/400 = 0.51 because the system percolates when the 204th site is opened.

## API:
`
public class PercolationStats {
   public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
   public double mean()                      // sample mean of percolation threshold
   public double stddev()                    // sample standard deviation of percolation threshold
   public double confidenceLo()              // low  endpoint of 95% confidence interval
   public double confidenceHi()              // high endpoint of 95% confidence interval

   public static void main(String[] args)    // test client (described below)
}
`

Tests: 
`
% java PercolationStats 200 100
mean                    = 0.5929934999999997
stddev                  = 0.00876990421552567
95% confidence interval = 0.5912745987737567, 0.5947124012262428

% java PercolationStats 200 100
mean                    = 0.592877
stddev                  = 0.009990523717073799
95% confidence interval = 0.5909188573514536, 0.5948351426485464


% java PercolationStats 2 10000
mean                    = 0.666925
stddev                  = 0.11776536521033558
95% confidence interval = 0.6646167988418774, 0.6692332011581226

% java PercolationStats 2 100000
mean                    = 0.6669475
stddev                  = 0.11775205263262094
95% confidence interval = 0.666217665216461, 0.6676773347835391
`

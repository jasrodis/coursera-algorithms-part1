public class Percolation {
    // N size of grid, all squares are closed
    private int size;
    private boolean[] grid;
    private WeightedQuickUnionUF unionFind;
    private WeightedQuickUnionUF weightedUnionFind;

    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException("Argument should be a positive number.");
        size = N;
        grid = new boolean[N * N + 2];
        unionFind = new WeightedQuickUnionUF(N * N + 2);
        weightedUnionFind = new WeightedQuickUnionUF(N * N + 1);
        grid[0] = true;
        for (int i = 1; i < N * N + 2; i++) {
            grid[i] = false;
        }
    }

    public void open(int i, int j) {
        if (i <= 0 || i > size)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > size)
            throw new IndexOutOfBoundsException("col index i out of bounds");

        grid[this.serializedIndex(i, j)] = true;

        if (i < size && this.isOpen(i + 1, j)) {
            unionFind.union(this.serializedIndex(i, j), this.serializedIndex(i + 1, j));
            weightedUnionFind.union(this.serializedIndex(i, j), this.serializedIndex(i + 1, j));

        }
        // Central
        if (i > 1 && this.isOpen(i - 1, j)) {
            unionFind.union(this.serializedIndex(i, j), this.serializedIndex(i - 1, j));
            weightedUnionFind.union(this.serializedIndex(i, j), this.serializedIndex(i - 1, j));
        }
        if (j < size && this.isOpen(i, j + 1)) {
            unionFind.union(this.serializedIndex(i, j), this.serializedIndex(i, j + 1));
            weightedUnionFind.union(this.serializedIndex(i, j), this.serializedIndex(i, j + 1));
        }
        if (j > 1 && this.isOpen(i, j - 1)) {
            unionFind.union(this.serializedIndex(i, j), this.serializedIndex(i, j - 1));
            weightedUnionFind.union(this.serializedIndex(i, j), this.serializedIndex(i, j - 1));
        }
        // Edge
        if (i == 1) {
            unionFind.union(this.serializedIndex(i, j), 0);
            weightedUnionFind.union(this.serializedIndex(i, j), 0);
        }
        if (i == size) {
            unionFind.union(this.serializedIndex(i, j), size * size + 1);
        }
    }

    private int serializedIndex(int i, int j) {
        return (i - 1) * size + j;
    }

    public boolean isOpen(int i, int j) {
        if (i <= 0 || i > size)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > size)
            throw new IndexOutOfBoundsException("col index i out of bounds");
        return grid[this.serializedIndex(i, j)];
    }

    public boolean isFull(int i, int j) {
        if (i <= 0 || i > size)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > size)
            throw new IndexOutOfBoundsException("col index i out of bounds");
        return weightedUnionFind.connected(this.serializedIndex(i, j), 0);
    }

    public boolean percolates() {
        return unionFind.connected(0, size * size + 1);
    }
}

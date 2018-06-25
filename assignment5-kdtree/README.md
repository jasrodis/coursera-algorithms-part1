
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<HTML>
<HEAD>

<TITLE>
Programming Assignment 5: Kd-Trees
</TITLE></HEAD>

<BODY>
<H2>Programming Assignment 5: Kd-Trees</H2>
</HEAD>

<hr>

Write a data type
to represent a set of points in the unit square 
(all points have <em>x</em>- and <em>y</em>-coordinates between 0 and 1)
using a <em>2d-tree</em> to support
efficient <em>range search</em> (find all of the points contained
in a query rectangle) and <em>nearest-neighbor search</em> (find a
closest point to a query point).
2d-trees have numerous applications, ranging from classifying astronomical objects
to computer animation to speeding up neural networks to mining data to image retrieval.

<p>
<center>
<img src = "kdtree-ops.png" width = 600 alt = "Range search and k-nearest neighbor">
</center>

<p><br><b>Geometric primitives.</b>
To get started, use the following geometric primitives for points and
axis-aligned rectangles in the plane.

<p>
<center>
<img src = "RectHV.png" width = 450 alt = "Geometric primitives">
</center>

<ul>

<p><li>
The immutable data type <a href = "https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/Point2D.html">Point2D</a> 
(part of <code>algs4.jar</code>) represents points in the plane.
Here is the subset of its API that you may use:

<blockquote>
<pre>
<b>public class Point2D implements Comparable&lt;Point2D&gt; {</b>
<b>   public Point2D(double x, double y)              </b><font color = gray>// construct the point (x, y)</font>
<b>   public  double x()                              </b><font color = gray>// x-coordinate</font> 
<b>   public  double y()                              </b><font color = gray>// y-coordinate</font> 
<b>   public  double distanceTo(Point2D that)         </b><font color = gray>// Euclidean distance between two points</font> 
<b>   public  double distanceSquaredTo(Point2D that)  </b><font color = gray>// square of Euclidean distance between two points</font> 
<b>   public     int compareTo(Point2D that)          </b><font color = gray>// for use in an ordered symbol table</font> 
<b>   public boolean equals(Object that)              </b><font color = gray>// does this point equal that object?</font> 
<b>   public    void draw()                           </b><font color = gray>// draw to standard draw</font> 
<b>   public  String toString()                       </b><font color = gray>// string representation</font> 
<b>}</b>
</pre>
</blockquote>


<p><li>
The immutable data type <a href = "https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/RectHV.html">RectHV</a>
(part of <code>algs4.jar</code>)
represents axis-aligned rectangles.
Here is the subset of its API that you may use:


<blockquote>
<pre>
<b>public class RectHV {</b>
<b>   public    RectHV(double xmin, double ymin,      </b><font color = gray>// construct the rectangle [xmin, xmax] x [ymin, ymax]</font> 
<b>                    double xmax, double ymax)      </b><font color = gray>// throw a java.lang.IllegalArgumentException if (xmin > xmax) or (ymin > ymax)</font>
<b>   public  double xmin()                           </b><font color = gray>// minimum x-coordinate of rectangle</font> 
<b>   public  double ymin()                           </b><font color = gray>// minimum y-coordinate of rectangle</font> 
<b>   public  double xmax()                           </b><font color = gray>// maximum x-coordinate of rectangle</font> 
<b>   public  double ymax()                           </b><font color = gray>// maximum y-coordinate of rectangle</font> 
<b>   public boolean contains(Point2D p)              </b><font color = gray>// does this rectangle contain the point p (either inside or on boundary)?</font> 
<b>   public boolean intersects(RectHV that)          </b><font color = gray>// does this rectangle intersect that rectangle (at one or more points)?</font> 
<b>   public  double distanceTo(Point2D p)            </b><font color = gray>// Euclidean distance from point p to closest point in rectangle</font> 
<b>   public  double distanceSquaredTo(Point2D p)     </b><font color = gray>// square of Euclidean distance from point p to closest point in rectangle</font> 
<b>   public boolean equals(Object that)              </b><font color = gray>// does this rectangle equal that object?</font> 
<b>   public    void draw()                           </b><font color = gray>// draw to standard draw</font> 
<b>   public  String toString()                       </b><font color = gray>// string representation</font> 
<b>}</b>
</pre>
</blockquote>

</ul>

Do not modify these data types.

<p><b>Brute-force implementation.</b>
Write a mutable data type <code>PointSET.java</code> that represents a set of
points in the unit square.
Implement the following API by using a red–black BST:

<blockquote>
<pre>
<b>public class PointSET {</b>
<b>   public         PointSET()                               </b><font color = gray>// construct an empty set of points</font> 
<b>   public           boolean isEmpty()                      </b><font color = gray>// is the set empty?</font> 
<b>   public               int size()                         </b><font color = gray>// number of points in the set</font> 
<b>   public              void insert(Point2D p)              </b><font color = gray>// add the point to the set (if it is not already in the set)</font>
<b>   public           boolean contains(Point2D p)            </b><font color = gray>// does the set contain point p?</font> 
<b>   public              void draw()                         </b><font color = gray>// draw all points to standard draw</font> 
<b>   public Iterable&lt;Point2D&gt; range(RectHV rect)             </b><font color = gray>// all points that are inside the rectangle (or on the boundary)</font> 
<b>   public           Point2D nearest(Point2D p)             </b><font color = gray>// a nearest neighbor in the set to point p; null if the set is empty</font> 

<b>   public static void main(String[] args)                  </b><font color = gray>// unit testing of the methods (optional)</font> 
<b>}</b>
</pre>
</blockquote>

<p><em>Implementation requirements.&nbsp;</em>
You must use either
<a href = "https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/SET.html"><code>SET</code></a> or
<a href = "https://docs.oracle.com/javase/8/docs/api/java/util/TreeSet.html"><code>java.util.TreeSet</code></a>;
do not implement your own red–black BST.

<p><em>Corner cases.&nbsp;</em>
Throw a <code>java.lang.IllegalArgumentException</code> if any argument is null.

<em>Performance requirements.&nbsp;</em>
Your implementation should support <code>insert()</code> and <code>contains()</code> in time
proportional to the logarithm of the number of points in the set in the worst case; it should support
<code>nearest()</code> and <code>range()</code> in time proportional to the number of points in the set.

<p><b>2d-tree implementation.</b>
Write a mutable data type <code>KdTree.java</code> that uses a 2d-tree to 
implement the same API (but replace <code>PointSET</code> with <code>KdTree</code>).
A <em>2d-tree</em> is a generalization of a BST to two-dimensional keys.
The idea is to build a BST with points in the nodes,
using the <em>x</em>- and <em>y</em>-coordinates of the points
as keys in strictly alternating sequence.

<ul>
<p><li><em>Search and insert.</em> 
The algorithms for search and insert are similar to those for
BSTs, but at the root we use the <em>x</em>-coordinate
(if the point to be inserted has a smaller <em>x</em>-coordinate
than the point at the root, go left; otherwise go right);
then at the next level, we use the <em>y</em>-coordinate
(if the point to be inserted has a smaller <em>y</em>-coordinate
than the point in the node, go left; otherwise go right);
then at the next level the <em>x</em>-coordinate, and so forth.
</ul>

<p>

<blockquote>
<TABLE BORDER = 0 CELLPADDING =2 CELLSPACING = 0>



</table>
</blockquote>

<ul>
<p><li><em>Draw.</em> 
A 2d-tree divides the unit square in a simple way: all the points to the
left of the root go in the left subtree; all those to the right go in 
the right subtree; and so forth, recursively.
Your <code>draw()</code> method should draw all of the points to standard draw
in black and the subdivisions in red (for vertical splits) and blue (for 
horizontal splits).
This method need not be efficient&mdash;it is primarily for debugging.

</ul>


<p>
The prime advantage of a 2d-tree over a BST
is that it supports efficient
implementation of range search and nearest-neighbor search.
Each node corresponds to an axis-aligned rectangle in the unit square,
which encloses all of the points in its subtree.
The root corresponds to the unit square; the left and right children
of the root corresponds to the two rectangles
split by the <em>x</em>-coordinate of the point at the root; and so forth.

<ul>

<p><li><em>Range search.</em>
To find all points contained in a given query rectangle, start at the root
and recursively search for points in <em>both</em> subtrees using the following
<em>pruning rule</em>:  if the query rectangle does not intersect the rectangle 
corresponding to a node, there is no need to explore that node (or its subtrees).
A subtree is searched only if it might contain a point contained in
the query rectangle.

<p><li><em>Nearest-neighbor search.</em>
To find a closest point to a given query point, start at the root
and recursively search in <em>both</em> subtrees using the following <em>pruning rule</em>:
if the closest point discovered so far is closer than the distance 
between the query point and the rectangle corresponding to a node,
there is no need to explore that node (or its subtrees).
That is, search a node only only if it might contain a point
that is closer than the best one found so far.
The effectiveness of the pruning rule depends on quickly finding a 
nearby point. To do this, organize the recursive method so that when 
there are two possible subtrees to go down, you always choose 
<em>the subtree
that is on the same side of the splitting line as the query point</em>
as  the first subtree to explore&mdash;the closest point
found while exploring the first
subtree may enable pruning of the second subtree.

<!--
<p><li><em>k nearest-neighbor search.</em>
Similar to finding the nearest-neighbor but prune if the <em>k</em>th closest point
discovered so far is closer than the distance between the query point
and the rectangle corresponding to a node.
-->

</ul>


<p><b>Clients.</b>&nbsp;
You may use the following interactive client programs to test and debug your code.
<ul>
<p><li>
<a href = "../testing/kdtree/KdTreeVisualizer.java">KdTreeVisualizer.java</a>
computes and draws the 2d-tree that results from the sequence of points clicked by the
user in the standard drawing window.

<p><li>
<a href = "../testing/kdtree/RangeSearchVisualizer.java">RangeSearchVisualizer.java</a>
reads a sequence of points from a file (specified as a command-line argument) and inserts those points
into a 2d-tree. Then, it performs range searches on the axis-aligned rectangles dragged
by the user in the standard drawing window.

<p><li>
<a href = "../testing/kdtree/NearestNeighborVisualizer.java">NearestNeighborVisualizer.java</a>
reads a sequence of points from a file (specified as a command-line argument) and inserts those points
into a 2d-tree. Then, it performs nearest-neighbor queries on the 
point corresponding
to the location of the mouse in the standard drawing window.

</ul>

<p><b>Analysis of running time and memory usage (optional and not graded).</b>&nbsp;

<ul>
<p><li> Give the total memory usage in bytes (using tilde notation)
of your 2d-tree data structure as a function of the 
number of points <em>n</em>, using the memory-cost model from lecture and Section 1.4 of the textbook.
Count all memory that is used by your 2d-tree, including
memory for the nodes, points, and rectangles.

<p><li> Give the expected running time in seconds (using tilde notation)
to build a 2d-tree on <em>n</em> random points in the unit square.
(Do not count the time to read in the points from standard input.)

<p><li> How many nearest-neighbor calculations can your 2d-tree implementation
perform per second for
<a href = "../testing/kdtree/input100K.txt">input100K.txt</a>
(100,000 points) and
<a href = "../testing/kdtree/input1M.txt">input1M.txt</a>
(1 million points),
where the query points are random points in the unit square?
(Do not count the time to read in the points or to build the 2d-tree.)
Repeat this question but with the brute-force implementation.


</ul>


</BODY>

</HTML>


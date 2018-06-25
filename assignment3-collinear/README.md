
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<HTML>
<HEAD>


</HEAD>


<BODY>
<H2>Programming Assignment 3: Pattern Recognition</H2>

<p>
<br>
Write a program to recognize line patterns in a given set of points.

<p>
Computer vision involves analyzing patterns in visual images and
reconstructing the real-world objects that produced them.  The process
is often broken up into two phases: <em>feature detection</em> and
<em>pattern recognition</em>. Feature detection involves selecting
important features of the image; pattern recognition involves
discovering patterns in the features. We will investigate a
particularly clean pattern recognition problem involving points and
line segments.  This kind of pattern recognition arises in many other
applications such as statistical data analysis.

<p>
<b>The problem.</b>
Given a set of <em>n</em> distinct points in the plane, 
find every (maximal) line segment that connects a subset of 4 or more of the points.
<p>


<p>
<b>Point data type.</b>
Create an immutable data type <code>Point</code> that represents a point in the plane
by implementing the following API:

<blockquote>
<pre>
<b>public class Point implements Comparable&lt;Point&gt; {</b>
<font color = gray>   public Point(int x, int y)                         // constructs the point (x, y)</font>

<font color = gray>   public   void draw()                               // draws this point</font>
<font color = gray>   public   void drawTo(Point that)                   // draws the line segment from this point to that point</font>
<font color = gray>   public String toString()                           // string representation</font>

<b>   public               int compareTo(Point that)</b>     <font color = gray>// compare two points by y-coordinates, breaking ties by x-coordinates</font>
<b>   public            double slopeTo(Point that)</b>       <font color = gray>// the slope between this point and that point</font>
<b>   public Comparator&lt;Point&gt; slopeOrder()</b>              <font color = gray>// compare two points by slopes they make with this point</font>
<b>}</b>
</pre>
</blockquote>

To get started, use the data type
<a href = "../testing/collinear/Point.java">Point.java</a>,
which implements the constructor and the
<code>draw()</code>, <code>drawTo()</code>, and <code>toString()</code> methods.
Your job is to add the following components.


<ul>


<p><li> The <code>compareTo()</code> method should compare points by their <em>y</em>-coordinates,
breaking ties by their <em>x</em>-coordinates.
Formally, the invoking point
(<em>x</em><sub>0</sub>, <em>y</em><sub>0</sub>)
is <em>less than</em> the argument point
(<em>x</em><sub>1</sub>, <em>y</em><sub>1</sub>)
if and only if either <em>y</em><sub>0</sub> &lt; <em>y</em><sub>1</sub> or if
<em>y</em><sub>0</sub> = <em>y</em><sub>1</sub> and <em>x</em><sub>0</sub> &lt; <em>x</em><sub>1</sub>.

<p><li> The <code>slopeTo()</code> method should return the slope between the invoking point
(<em>x</em><sub>0</sub>, <em>y</em><sub>0</sub>) and the argument point
(<em>x</em><sub>1</sub>, <em>y</em><sub>1</sub>), which is given by the formula
(<em>y</em><sub>1</sub> &minus; <em>y</em><sub>0</sub>) / (<em>x</em><sub>1</sub> &minus; <em>x</em><sub>0</sub>).
Treat the slope of a horizontal line segment as positive zero;
treat the slope of a vertical line segment as positive infinity;
treat the slope of a degenerate line segment (between a point and itself) as negative infinity.

<p><li> The <code>slopeOrder()</code> method should return a comparator that compares its two argument
points by the slopes they make with the invoking point (<em>x</em><sub>0</sub>, <em>y</em><sub>0</sub>).
Formally, the point (<em>x</em><sub>1</sub>, <em>y</em><sub>1</sub>) is <em>less than</em>
the point (<em>x</em><sub>2</sub>, <em>y</em><sub>2</sub>) if and only if the slope
(<em>y</em><sub>1</sub> &minus; <em>y</em><sub>0</sub>) / (<em>x</em><sub>1</sub> &minus; <em>x</em><sub>0</sub>) 
is less than the slope
(<em>y</em><sub>2</sub> &minus; <em>y</em><sub>0</sub>) / (<em>x</em><sub>2</sub> &minus; <em>x</em><sub>0</sub>).
Treat horizontal, vertical, and degenerate line segments as in the <code>slopeTo()</code> method.

<p><li> Do <em>not</em> override the <code>equals()</code> or <code>hashCode()</code> methods.

</ul>

<p><em>Corner cases.</em> To avoid potential complications with integer overflow or floating-point precision,
you may assume that the constructor arguments <code>x</code> and <code>y</code> are each between 0 and 32,767.

<p>
<b>Line segment data type.</b>
To represent line segments in the plane, use the data type
<a href = "../testing/collinear/LineSegment.java">LineSegment.java</a>,
which has the following API:

<blockquote>
<pre>
<b>public class LineSegment {</b>
<font color = gray>   public LineSegment(Point p, Point q)        // constructs the line segment between points p and q</font>
<font color = gray>   public   void draw()                        // draws this line segment</font>
<font color = gray>   public String toString()                    // string representation</font>
<b>}</b>
</pre>
</blockquote>

<p>
<b>Brute force.</b>
Write a program <code>BruteCollinearPoints.java</code> that examines 4 
points at a time and checks whether they all lie on the same line segment, returning all such line segments.
To check whether the 4 points <em>p</em>, <em>q</em>, <em>r</em>, and <em>s</em> are collinear,
check whether the three slopes between <em>p</em> and <em>q</em>, 
between <em>p</em> and <em>r</em>, and between <em>p</em> and <em>s</em>
are all equal.


<blockquote>
<pre>
<b>public class BruteCollinearPoints {</b>
<b>   public BruteCollinearPoints(Point[] points)    </b><font color = gray>// finds all line segments containing 4 points</font>
<b>   public           int numberOfSegments()        </b><font color = gray>// the number of line segments</font>
<b>   public LineSegment[] segments()                </b><font color = gray>// the line segments</font>
<b>}</b>
</pre>
</blockquote>


<p>
The method <code>segments()</code> should include each line segment containing 4 points exactly once.

If 4 points appear on a line segment in the
order <em>p</em>&rarr;<em>q</em>&rarr;<em>r</em>&rarr;<em>s</em>,
then you should include either the line segment
<em>p</em>&rarr;<em>s</em> or <em>s</em>&rarr;<em>p</em> (but not both)
and you should not include <em>subsegments</em> such as <em>p</em>&rarr;<em>r</em> or
<em>q</em>&rarr;<em>r</em>.
For simplicity,
we will not supply any input to <code>BruteCollinearPoints</code> that has 5 or more collinear points.

<p><em>Corner cases.</em>
Throw a <code>java.lang.IllegalArgumentException</code> if the argument to the constructor
is <code>null</code>, if any point in the array is <code>null</code>, or if
the argument to the constructor contains a repeated point.



<p><em>Performance requirement.</em>
The order of growth of the running time of your program should be
<em>n</em><sup>4</sup> in the worst case and 
it should use space proportional to <em>n</em> plus the number of line segments returned.


<p>
<b>A faster, sorting-based solution.</b>
Remarkably, it is possible to solve the problem much faster than the
brute-force solution described above.
Given a point <em>p</em>, the following method determines whether <em>p</em>
participates in a set of 4 or more collinear points.
<ul>
<li>Think of <em>p</em> as the origin.
<p><li>For each other point <em>q</em>, determine the slope it makes with <em>p</em>.
<p><li>Sort the points according to the slopes
they makes with <em>p</em>.
<p><li>Check if any 3 (or more) adjacent points in the sorted order have equal
slopes with respect to <em>p</em>.
If so, these points, together with <em>p</em>, are collinear.
</ul>

Applying this method for each of the <em>n</em> points in turn yields an
efficient algorithm to the problem.
The algorithm solves the problem because points that have equal 
slopes with respect to <em>p</em> are collinear, and sorting brings such points together.
The algorithm is fast because the bottleneck operation is sorting.


Write a program <code>FastCollinearPoints.java</code> that implements this algorithm.

<blockquote>
<pre>
<b>public class FastCollinearPoints {</b>
<b>   public FastCollinearPoints(Point[] points)     </b><font color = gray>// finds all line segments containing 4 or more points</font>
<b>   public           int numberOfSegments()        </b><font color = gray>// the number of line segments</font>
<b>   public LineSegment[] segments()                </b><font color = gray>// the line segments</font>
<b>}</b>
</pre>
</blockquote>

<p>
The method <code>segments()</code> should include each <em>maximal</em> line segment
containing 4 (or more) points exactly once.
For example, if 5 points appear on a line segment in the
order <em>p</em>&rarr;<em>q</em>&rarr;<em>r</em>&rarr;<em>s</em>&rarr;<em>t</em>,
then do not include the subsegments <em>p</em>&rarr;<em>s</em> or <em>q</em>&rarr;<em>t</em>.


<p><em>Corner cases.</em>
Throw a <code>java.lang.IllegalArgumentException</code> if the argument to the constructor
is <code>null</code>, if any point in the array is <code>null</code>, or
if the argument to the constructor contains a repeated point.

<p><em>Performance requirement.</em>
The order of growth of the running time of your program should be
<em>n</em><sup>2</sup> log <em>n</em> in the worst case and 
it should use space proportional to <em>n</em> plus the number of line segments returned.
<code>FastCollinearPoints</code> should work properly even if the input has 5 or more collinear points.


<p>
<b>Sample client.</b> 
This client program takes the name of an input file as a command-line argument;
read the input file (in the format specified below);
prints to standard output the line segments that your program discovers, one per line;
and draws to standard draw the line segments.

<blockquote>
<pre>
public static void main(String[] args) {

    <font color = gray>// read the n points from a file</font>
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    <font color = gray>// draw the points</font>
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    <font color = gray>// print and draw the line segments</font>
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
</pre>
</blockquote>



<p>
<b>Input format.</b>
We supply several sample input files (suitable for use with the test client above)
 in the following format:
An integer <em>n</em>, followed by <em>n</em>
pairs of integers (<em>x</em>, <em>y</em>), each between 0 and 32,767.
Below are two examples.

<blockquote>
<pre>
% <b>more input6.txt</b>       % <b>more input8.txt</b>
6                       8
19000  10000             10000      0
18000  10000                 0  10000
32000  10000              3000   7000
21000  10000              7000   3000
 1234   5678             20000  21000
14000  10000              3000   4000
                         14000  15000
                          6000   7000
</pre>
</blockquote>


<blockquote><pre>
% <b>java-algs4 BruteCollinearPoints input8.txt</b>
(10000, 0) -> (0, 10000) 
(3000, 4000) -> (20000, 21000) 

% <b>java-algs4 FastCollinearPoints input8.txt</b>
(3000, 4000) -> (20000, 21000) 
(0, 10000) -> (10000, 0)

% <b>java-algs4 FastCollinearPoints input6.txt</b>
(14000, 10000) -> (32000, 10000) 
</pre>
</blockquote>



<p>
<b>Deliverables.</b>
Submit only the files
<code>BruteCollinearPoints.java</code>, <code>FastCollinearPoints.java</code>, and <code>Point.java</code>.
We will supply <code>LineSegment.java</code> and <code>algs4.jar</code>.
You may not call any library functions other those in
<code>java.lang</code>, <code>java.util</code>, and <code>algs4.jar</code>.
You may use library functions in <code>java.util</code> only if they have already
been introduced in the course. For example, you may use
<code>Arrays.sort()</code>, but not <code>java.util.HashSet</code>.

</BODY>


</HTML>



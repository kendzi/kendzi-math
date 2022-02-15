package kendzi.math.geometry.ray;

import org.joml.Vector3d;
import org.joml.Vector3dc;

import kendzi.math.geometry.Sphere3d;

public class Ray3dUtil {

    public static Double intersect(Ray3d ray, Sphere3d sphere) {
        return intersect(ray, sphere.getCenter(), sphere.getRadius());
    }

    public static Double intersect(Ray3d ray, Vector3dc sphereCenter, double sphereRadius) {

//        double r = sphere.getRadius();
        double r = sphereRadius;

        Vector3d ray_o = new Vector3d(ray.getPoint()).sub(sphereCenter);
//        ray_o.sub(sphere.getCenter());


        Vector3dc ray_d = ray.getVector();
//        Point3d ray_o = ray.getPoint();



        // Compute A, B and C coefficients
        double a = ray_d.dot(ray_d);
        double b = 2 * ray_d.dot(ray_o);
        double c = ray_o.dot(ray_o) - r * r;

        double t;

        // Find discriminant
        double disc = b * b - 4 * a * c;

        // if discriminant is negative there are no real roots, so return
        // false as ray misses sphere
        if (disc < 0) {
            return null;
        }

        // compute q as described above
        double distSqrt = Math.sqrt(disc);
        double q;
        if (b < 0) {
            q = (-b - distSqrt) / 2.0;
        } else {
            q = (-b + distSqrt) / 2.0;
        }

        // compute t0 and t1
        double t0 = q / a;
        double t1 = c / q;

        // make sure t0 is smaller than t1
        if (t0 > t1) {
            // if t0 is bigger than t1 swap them around
            double temp = t0;
            t0 = t1;
            t1 = temp;
        }

        // if t1 is less than zero, the object is in the ray's negative direction
        // and consequently the ray misses the sphere
        if (t1 < 0) {
//            return false;
            return null;
        }

        // if t0 is less than zero, the intersection point is at t1
        if (t0 < 0) {
            t = t1;
//            return true;
            return t;
        }
        // else the intersection point is at t0
        else {
            t = t0;
//            return true;
            return t;
        }
    }

    /**
     *  Test intersection of sphere with center in 0,0 radius r, and ray
     * @param ray
     * @param r
     * @return
     *
     * @see {http://wiki.cgsociety.org/index.php/Ray_Sphere_Intersection}
     */
    Double intersect(Ray3d ray, double r) {

        double t;

        Vector3dc ray_d = ray.getVector();
        Vector3dc ray_o = ray.getPoint();

        // Compute A, B and C coefficients
        double a = ray_d.dot(ray_d);
        double b = 2 * ray_d.dot(ray_o);
        double c = ray_o.dot(ray_o) - (r * r);

        // Find discriminant
        double disc = b * b - 4 * a * c;

        // if discriminant is negative there are no real roots, so return
        // false as ray misses sphere
        if (disc < 0) {
            return null;
        }

        // compute q as described above
        double distSqrt = Math.sqrt(disc);
        double q;
        if (b < 0) {
            q = (-b - distSqrt) / 2.0;
        } else {
            q = (-b + distSqrt) / 2.0;
        }

        // compute t0 and t1
        double t0 = q / a;
        double t1 = c / q;

        // make sure t0 is smaller than t1
        if (t0 > t1) {
            // if t0 is bigger than t1 swap them around
            double temp = t0;
            t0 = t1;
            t1 = temp;
        }

        // if t1 is less than zero, the object is in the ray's negative direction
        // and consequently the ray misses the sphere
        if (t1 < 0) {
//            return false;
            return null;
        }

        // if t0 is less than zero, the intersection point is at t1
        if (t0 < 0) {
            t = t1;
//            return true;
            return t;
        }
        // else the intersection point is at t0
        else {
            t = t0;
//            return true;
            return t;
        }
    }

    /**
     * Returns the dot product of this vector and vector v1.
     *
     * @param v1
     *            the other vector
     * @return the dot product of this and v1
     * @deprecated Use {@link Vector3dc#dot(Vector3dc)} instead
     */
    public static double dot(Vector3dc v, Vector3dc v1) {
        return v.dot(v1);
    }

    /**
     * Vector subtraction
     * @param v
     * @param v1
     * @return A <i>new</i> vector
     * @deprecated Use {@link Vector3d#sub(Vector3dc)} if at all possible.
     */
    public static Vector3d sub(Vector3dc v, Vector3dc v1) {
        return v.sub(v1, new Vector3d());
    }

    /** Return closest point to ray, The point is lies on ray baseRay.
     * @param ray
     * @param baseRay
     * @return
     */
    public static Vector3dc closestPointOnBaseRay(Ray3d ray, Ray3d baseRay) {
        //http://geomalgorithms.com/a07-_distance.html
        Ray3d P = ray;
        Ray3d Q = baseRay;

        Vector3dc u = P.getVector();
        Vector3dc v = Q.getVector();

        Vector3dc Q0 = Q.getPoint();
        Vector3dc P0 = P.getPoint();

        Vector3d w0 = P0.sub(Q0, new Vector3d());

        double a = u.dot(u);
        double b = u.dot(v);
        double c = v.dot(v);
        double d = u.dot(w0);
        double e = v.dot(w0);

        double m = a * c - b * b;
        if (m == 0) {
            return new Vector3d(Q0);
        }

        double tc = (a * e - b * d) / m;

        return new Vector3d(
                Q0.x() + v.x() * tc,
                Q0.y() + v.y() * tc,
                Q0.z() + v.z() * tc
                );

    }
}

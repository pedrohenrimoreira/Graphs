package models;
import java.util.List;

public class Result {
     public List<Integer> centers;
        public int radius;

        public Result(List<Integer> centers, int radius) {
            this.centers = centers;
            this.radius = radius;
        }
}
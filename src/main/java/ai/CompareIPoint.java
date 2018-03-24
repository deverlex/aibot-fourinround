package ai;

import java.util.Comparator;

public class CompareIPoint implements Comparator<IPoint> {

    @Override
    public int compare(IPoint o1, IPoint o2) {
        return o1.getDanger() - o2.getDanger();
    }
}

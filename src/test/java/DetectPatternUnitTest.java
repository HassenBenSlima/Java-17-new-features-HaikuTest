import org.assertj.core.api.Assertions;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DetectPatternUnitTest {

    MutableList<Integer> list;

    @Before
    public void getList() {
        this.list = FastList.newListWith(1, 8, 5, 41, 31, 17, 23, 38);
    }

    @Test
    public void whenDetect_thenCorrect() {
        Integer result = list.detect(Predicates.greaterThan(30));

        Assertions.assertThat(result).isEqualTo(41);
    }

    @Test
    public void whenAnySatisfiesCondition_thenCorrect() {
        boolean result = list.anySatisfy(Predicates.greaterThan(30));

        assertTrue(result);
    }

    @Test
    public void whenAnySatisfiesCondition_thenCorrect2() {
        boolean result = list.allSatisfy(Predicates.greaterThan(0));

        assertTrue(result);
    }
}
package Asserts;

import Managers.AssertManager;
import lombok.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


@RequiredArgsConstructor
public class ApiAsserts {
    @Getter String expected;
    @NonNull @Setter String actual;
    @NonNull
    AssertManager parent;

    public ApiAsserts setExpected(String expected){
        this.expected = expected;
        return this;
    }

    public void assertStatus() { assertThat(actual, is(equalTo(expected))); }
    public void assertLength() { assertThat(actual.length(), is(equalTo(140))); }

}

package racingcar.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import camp.nextstep.edu.missionutils.Console;
import java.io.ByteArrayInputStream;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class ConsoleInputTest {
    @Mock
    static InputValidator inputValidator = mock(InputValidator.class);

    @InjectMocks
    static ConsoleInput consoleInput = new ConsoleInput();

    @BeforeAll
    static void setMock() {
        when(inputValidator.validateCarNames(ArgumentMatchers.anyString()))
                .thenReturn(true);
    }

    @AfterEach
    void clearSystemIn() {
        Console.close();
    }

    @DisplayName("자동차 이름 입력")
    @Nested
    class InputCarNames {
        @Test
        @DisplayName("정상동작")
        void goodCase() {
            //given
            byte[] buf = "붕붕카,타요버스,시내버스,토마스버스".getBytes();
            System.setIn(new ByteArrayInputStream(buf));

            //when
            List<String> carNames = consoleInput.getCarNames();

            //then
            assertThat(carNames).contains("붕붕카", "타요버스", "시내버스", "토마스버스");
            assertThat(carNames).containsExactly("붕붕카", "타요버스", "시내버스", "토마스버스");
        }

        @Test
        @DisplayName("쉼표구분")
        void 쉼표_구분() {
            //given
            byte[] buf = "붕,카,타,요,버,스,시,내,토,마".getBytes();
            System.setIn(new ByteArrayInputStream(buf));

            //when
            List<String> carNames = consoleInput.getCarNames();

            //then
            assertThat(carNames).contains("붕", "카", "타", "요", "버", "스", "시", "내", "토", "마");
            assertThat(carNames).containsExactly("붕", "카", "타", "요", "버", "스", "시", "내", "토", "마");
        }

        @Test
        @DisplayName("공백포함")
        void 공백_포함() {
            //given
            byte[] buf = "붕  ,   카   , 타,   요,버  ,스,시".getBytes();
            System.setIn(new ByteArrayInputStream(buf));

            //when
            List<String> carNames = consoleInput.getCarNames();

            //then
            assertThat(carNames).contains("붕", "카", "타", "요", "버", "스", "시");
            assertThat(carNames).containsExactly("붕", "카", "타", "요", "버", "스", "시");
        }
    }
}
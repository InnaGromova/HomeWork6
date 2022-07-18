package example.org;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.util.Arrays.asList;


public class Test1 {
    SelenideElement surname = $("#surname");
    SelenideElement name = $("#name");
    SelenideElement middle = $("#middle");
    SelenideElement getSignature =  $("#GetSignature");
    SelenideElement signature =  $("#Signature_1");
    SelenideElement search =  $("#title-search-input");
    SelenideElement searchbutton = $("input[type='submit']");

    @ParameterizedTest(name = "При вводе полного имени {0} {1} {2} подпись генерируется")
    @CsvSource({
            "Gromova,Inna,Sergeevna",
            "Gromova,Inna,"
    })
    void fullnameTest(ArgumentsAccessor argumentsAccessor) {
        Selenide.open("https://podpis-online.ru/");
        surname.setValue(argumentsAccessor.getString(0));
        name.setValue(argumentsAccessor.getString(1));
        middle.setValue(argumentsAccessor.getString(2));
        getSignature.click();
        signature.shouldBe(visible);
    }
    static Stream<Arguments> bleachnarutoSearch() {
        return Stream.of(
                Arguments.of("naruto", asList("Hatake Kakashi Anbu Black","Namikaze Minato")),
                Arguments.of("bleach", asList("Hitsugaya Toushirou","Kurosaki Ichigo"))
        );
    }
    @MethodSource(value = "bleachnarutoSearch")
    @ParameterizedTest(name = "При поиске в аниме-магазине {0} в результатах отображается {1}")
    void bleachnarutoSearch(String searchData, List<String> expectedResult){
        Selenide.open("https://nodanoshi.net/catalog/32-figurki_anime/");
        search.setValue(searchData);
        searchbutton.click();
        $$("#content").find(text(String.valueOf(CollectionCondition.texts(expectedResult))));
    }
    @ValueSource(strings = {"Kimi no na wa", "Tenki no ko", "Sen to Chihiro no Kamikakushi"})
    @ParameterizedTest(name = "При поиске в аниме-магазине {0} в результатах отображается {0}")
    void animeSearch(String setanime){
        Selenide.open("https://nodanoshi.net/catalog/32-figurki_anime/");
        search.setValue(setanime);
        searchbutton.click();
        $$("#content").find(text(String.valueOf(CollectionCondition.texts(setanime))));
    }

}



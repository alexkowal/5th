package neuralNetworks.task1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.ToString;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@ToString
@JsonView
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class Graph {
    @JsonProperty("arcs")
    List<Arc> arcList = Lists.newArrayList();


    public void readArcs(File file) throws Exception {
        int row = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s;

            while ((s = br.readLine()) != null) {
                Iterable<String> split = Splitter.on("),").trimResults().split(s);

                for (String s1 : split) {
                    s1 = s1.substring(1, s1.length());
                    if (s1.charAt(s1.length() - 1) == ')')
                        s1 = s1.substring(0, s1.length() - 1);
                    Iterable<String> splitString = Splitter.on(",").trimResults().split(s1);
                    Arc arc = new Arc();
                    ArrayList<String> strings = Lists.newArrayList(splitString);
                    arc.setV1(Integer.parseInt(strings.get(0)));
                    arc.setV2(Integer.parseInt(strings.get(1)));
                    arc.setIndex(Integer.parseInt(strings.get(2)));
                    arcList.add(arc);
                }
                row++;
            }

            System.out.println(arcList.toString());
            validateInput();
        } catch (Exception e) {
            System.out.println("Exception in row" + row + " \n" + e);
        }
    }

    private void validateInput() throws Exception {
        int i = 0;
        for (Arc arc : arcList) {
            for (Arc arc1 : arcList) {
                if (arc1.getV2() == arc.getV2())
                    if (arc.getIndex() == arc1.getIndex()) {
                        if (i > arcList.size())
                            throw new Exception("Одинаковый индекс дуг для вершины: " + arc.getV2());
                        else i++;
                    }
            }
        }
    }

    public void sortAndReturn() throws Exception {
        Comparator<Arc> comparator = Comparator.comparing(arc -> arc.getV2());
        comparator = comparator.thenComparing(Comparator.comparing(arc -> arc.getIndex()));
        Stream<Arc> arcStream = arcList.stream().sorted(comparator);
        List<Arc> sortedList = arcStream.collect(Collectors.toList());
        this.arcList = sortedList;
        validate();
        System.out.println(sortedList);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("/Users/aleksandr/5 курс 1 семестр/Code/src/main/java/neuralNetworks/task1/out.json"), this);
    }

    public void validate() throws Exception {
        int v1 = 0;
        int v2 = 0;
        int index = 0;
        Arc arc;
        for (int i = 0; i < arcList.size(); i++) {
            arc = arcList.get(i);
            index = arc.getIndex();
            v1 = arc.getV1();
            v2 = arc.getV2();
            if (index != 1)
                throw new Exception("Не правильный индекс для дуги " + arc.toString());
            while (arcList.get(i).getV2() == v2 && i + 1 < arcList.size()) {
                i++;
                if (arcList.get(i).getV2() == v2) {
                    if (arcList.get(i).getIndex() != index + 1)
                        throw new Exception("Не правильный индекс для дуги " + arc.toString());
                    index++;
                }
            }
        }
        Set<Integer> setV1 = Sets.newHashSet();
        Set<Integer> setV2 = Sets.newHashSet();
        arcList.stream().forEach(arc1 -> {
            setV1.add(arc1.getV1());
            setV2.add(arc1.getV2());
        });
    }

    private void checkIsLinear(Set<Integer> setV1) throws Exception {
        if (setV1.contains(1)) {
            List<Integer> l = Lists.newArrayList(setV1);
            for (int i = 1; i < l.size(); i++) {
                if (l.get(i) != l.get(i - 1) + 1)
                    throw new Exception("Вершины v1/v2 не являются линейно упорядоченными");
            }
        }
    }

}

package second.cryptoAnalysis.first.classes;

import com.google.common.collect.Lists;
import first.number_theoretic_methods_in_cryptography.task1.WrongInputException;

import java.io.IOException;
import java.util.List;

public class KasiskiExamination {
    private static Long GCD(Long a, Long b) throws WrongInputException {
        if (b == 0)
            return a;
        else return GCD(b, a % b);
    }

    public void calculate() throws IOException {
        String text = CryptoUtils.readText();
        List<Long> repeatCount = Lists.newArrayList();

    }

/*
*
* string text = File.ReadAllText("text.txt", Encoding.GetEncoding(1251));//считываем текст
List repeatCount = new List();//массив, который содержит все длины
//заполняем этот массив, ища расстояние между одинаковыми триграммами
for (int i = 0; i < text.Length - digramLength + 1; i++)
{ string temp = text.Substring(i, digramLength);
for (int j = i + 1; j < text.Length - digramLength + 1; j++)
 { string temp2 = text.Substring(j, digramLength);
  if(temp.Equals(temp2)) { repeatCount.Add(j - i); } } }
  int[] nods = new int[5000];//массив для подсчета количества НОД
   //В случае если НОД двух расстояний равен q, то увеличваем nods[q] на 1
   for (int i = 0; i < repeatCount.Count; ++i)
   for (int j = i + 1; j < repeatCount.Count; ++j)
   nods[gcd(repeatCount[i], repeatCount[j])]++;
   nods[0] = 0; //загоняем все в новый массив, чтобы удобно отсортировать
   List ans= new List();
for (int i = 2; i < 500; ++i)
{ ans.Add(new Pair() { Index = i, Value = nods[i] }); }
 IEnumerable anss = ans.OrderByDescending(p => p.Value).Take(10);//сортируем и берем первые 10 результатов
string stringAns = "";
//выводим ответ
foreach (var s in anss)
{
if(s.Value>0)
{
stringAns += s.Index + " ";
}
}
File.WriteAllText("ans.txt", stringAns);
}
}
}


*
* */
}

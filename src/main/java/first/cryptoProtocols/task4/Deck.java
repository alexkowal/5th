package first.cryptoProtocols.task4;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Data
public class Deck {
    Map<Integer, Card> deck = new HashMap<>();
    private List<String> suits = Lists.newArrayList("hearts", "clubs", "diamonds", "spades");
    private List<String> ranks = Lists.newArrayList("2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack",
            "Queen", "King", "Ace");

    public Deck initDeck() {
        Deck deck = new Deck();
        Random random = new Random();
        int k = 0;
        for (int i = 0; i < ranks.size(); i++) {
            for (int j = 0; j < suits.size(); j++) {
                deck.deck.put(k, new Card(ranks.get(i), suits.get(j), String.valueOf(System.currentTimeMillis() + random.nextInt(1000))));
                k++;
            }
        }
        return deck;
    }

    public Deck shuffleDeck(Deck deck) {
        for (int i = 0; i < deck.deck.size(); i++) {
            int r = i + (int) (Math.random() * (deck.deck.size() - i)); // случайная карта в колоде
            Card card = deck.deck.get(r);
            Card card2 = deck.deck.get(i);
            deck.deck.put(r, card2);
            deck.deck.put(i, card);
        }
        return deck;
    }
}

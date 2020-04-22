package search.analyzers;

import datastructures.concrete.ChainedHashSet;
import datastructures.concrete.KVPair;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import search.models.Webpage;

import java.net.URI;

/**
 * This class is responsible for computing how "relevant" any given document is
 * to a given search query.
 *
 * See the spec for more details.
 */
public class TfIdfAnalyzer {
    // This field must contain the IDF score for every single word in all
    // the documents.
    private IDictionary<String, Double> idfScores;

    // This field must contain the TF-IDF vector for each webpage you were given
    // in the constructor.
    //
    // We will use each webpage's page URI as a unique key.
    private IDictionary<URI, IDictionary<String, Double>> documentTfIdfVectors;
    private IDictionary<URI, Double> normDocumentVector;

    // Feel free to add extra fields and helper methods.
    // private IDictionary<URI, Double> NormQueryVector;

    public TfIdfAnalyzer(ISet<Webpage> webpages) {
        // Implementation note: We have commented these method calls out so your
        // search engine doesn't immediately crash when you try running it for the
        // first time.
        //
        // You should uncomment these lines when you're ready to begin working
        // on this class.
        this.normDocumentVector = new ChainedHashDictionary<>();
        this.idfScores = this.computeIdfScores(webpages);
        this.documentTfIdfVectors = this.computeAllDocumentTfIdfVectors(webpages);
    }

    // Note: this method, strictly speaking, doesn't need to exist. However,
    // we've included it so we can add some unit tests to help verify that your
    // constructor correctly initializes your fields.
    public IDictionary<URI, IDictionary<String, Double>> getDocumentTfIdfVectors() {
        return this.documentTfIdfVectors;
    }

    // Note: these private methods are suggestions or hints on how to structure your
    // code. However, since they're private, you're not obligated to implement
    // exactly
    // these methods: feel free to change or modify these methods however you want.
    // The
    // important thing is that your 'computeRelevance' method ultimately returns the
    // correct answer in an efficient manner.

    /**
     * Return a dictionary mapping every single unique word found in every single
     * document to their IDF score.
     */
    private IDictionary<String, Double> computeIdfScores(ISet<Webpage> pages) {
        IDictionary<String, Double> idfScore = new ChainedHashDictionary<String, Double>();
        IDictionary<String, Double> idfScore2 = new ChainedHashDictionary<String, Double>();
        int total = pages.size();
        for (Webpage page : pages) {
            IList<String> list = page.getWords();
            ISet<String> lookUp = new ChainedHashSet<String>();
            for (String word : list) {
                if (!lookUp.contains(word)) {
                    idfScore.put(word, idfScore.getOrDefault(word, 0.0) + 1);
                    lookUp.add(word);
                }
            }
        }
        for (KVPair<String, Double> pair : idfScore) {
            double log = Math.log(total / pair.getValue());
            idfScore2.put(pair.getKey(), log);
        }
        return idfScore2;
    }

    /**
     * Returns a dictionary mapping every unique word found in the given list to
     * their term frequency (TF) score.
     *
     * The input list represents the words contained within a single document.
     */
    private IDictionary<String, Double> computeTfScores(IList<String> words) {
        IDictionary<String, Double> tfScores = new ChainedHashDictionary<String, Double>();
        int total = words.size();
        for (String word : words) {
            tfScores.put(word, tfScores.getOrDefault(word, 0.0) + 1);
        }
        for (KVPair<String, Double> pair : tfScores) {
            tfScores.put(pair.getKey(), tfScores.get(pair.getKey()) / total);
        }
        return tfScores;
    }

    /**
     * See spec for more details on what this method should do.
     */
    private IDictionary<URI, IDictionary<String, Double>> computeAllDocumentTfIdfVectors(ISet<Webpage> pages) {
        // Hint: this method should use the idfScores field and
        // call the computeTfScores(...) method.
        IDictionary<URI, IDictionary<String, Double>> tfidfVector = 
                new ChainedHashDictionary<URI, IDictionary<String, Double>>();
        for (Webpage page : pages) {
            IList<String> words = page.getWords();
            IDictionary<String, Double> tf = computeTfScores(words);
            IDictionary<String, Double> tfidf = new ChainedHashDictionary<String, Double>();
            for (String word : words) {
                double tfTimesIDF = tf.get(word) * idfScores.get(word);
                tfidf.put(word, tfTimesIDF);
            }
            normDocumentVector.put(page.getUri(), norm(tfidf));
            tfidfVector.put(page.getUri(), tfidf);
        }
        return tfidfVector;
    }

    /**
     * Returns the cosine similarity between the TF-IDF vector for the given query
     * and the URI's document.
     *
     * Precondition: the given uri must have been one of the uris within the list of
     * webpages given to the constructor.
     */
    public Double computeRelevance(IList<String> query, URI pageUri) {
        // Note: The pseudocode we gave you is not very efficient. When implementing,
        // this method, you should:
        //
        // 1. Figure out what information can be precomputed in your constructor.
        // Add a third field containing that information.
        //
        // 2. See if you can combine or merge one or more loops.
        IDictionary<String, Double> documentVector = documentTfIdfVectors.get(pageUri);
        IDictionary<String, Double> queryVector = computeTfScores(query);
        for (String word : query) {
            if (idfScores.containsKey(word)) { // for word that doesn't exist
                queryVector.put(word, queryVector.get(word) * idfScores.get(word));
            }
        }
        double numerator = 0.0;
        for (String word : query) {
            double docWordScore = 0.0;
            double queryWordScore = 0.0;
            if (documentVector.containsKey(word)) {
                docWordScore = documentVector.get(word);
            }
            queryWordScore = queryVector.get(word);
            numerator += docWordScore * queryWordScore;
        }
        double denominator = normDocumentVector.get(pageUri) * norm(queryVector);
        if (denominator != 0) {
            return numerator / denominator;
        } else {
            return 0.0;
        }
    }

    private double norm(IDictionary<String, Double> vector) {
        double output = 0.0;
        for (KVPair<String, Double> pair : vector) {
            double score = pair.getValue();
            output += score * score;
        }
        return Math.sqrt(output);
    }
}

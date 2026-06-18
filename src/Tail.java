/**
 * CS214 Project 2026
 * Electronic Khoekhoegowab Dictionary
 *
 * File: Manager.java
 *
 * Student Number: 29004691
 * Name: Olivia Connellan
 *
 * Description:
 * This class manages the overall processing of dictionary entries.
 * It handles parsing of input lines, construction of dictionary objects,
 * expansion of abbreviations, handling of tildes, and search functionality.
 *
 * It also maintains the main dictionary and supports search by word,
 * definition, dialect, origin, and part of speech.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Manager {

    // =========================================================
    // 1. Stored dictionary data
    // =========================================================
    private static List<WordFinal> dictionary = new ArrayList<>();
    private static Map<String, List<WordFinal>> dialectIndex = new HashMap<>();
    private static Map<String, List<WordFinal>> originIndex = new HashMap<>();
    private static Map<String, List<WordFinal>> posIndex = new HashMap<>();
    
    // =========================================================
    // 2. Constants and patterns
    // =========================================================
    private static final Set<String> POS = Set.of(
        "a.", "abbr.", "adv.", "conj.", "excl.", "ideo.", 
        "n.", "num.", "o.m.", "postp.", 
        "v.", "v.i", "v.refl", "v.recip", 
        "v.t", "v.t/i", "v.t.stat", "v.tt.stat", "v.i.stat", "v.tt");

    private static final Set<String> VALID_DIALECTS = Set.of(
        "N", "D", "T", "Hm", "Bz", "S", "Namid", "ǂA", "ǂD", "V");

    public static final Map<String, String> ABBR = new HashMap<>();

    static {
    ABBR.put("anim.", "animal(s)");
    ABBR.put("app.", "appositive");
    ABBR.put("arch.", "archaic");
    ABBR.put("art.", "article");
    ABBR.put("aux.", "auxiliary verb");
    ABBR.put("b.", "been");
    ABBR.put("bef.", "before");
    ABBR.put("Bibl.", "Biblical language");
    ABBR.put("bot.", "botanical");
    ABBR.put("br.", "brother");
    ABBR.put("colloq.", "colloquial");
    ABBR.put("cond.", "condition");
    ABBR.put("dem.", "demonstrative");
    ABBR.put("derog.", "derogatory");
    ABBR.put("did.", "didactic");
    ABBR.put("dign.", "dignified");
    ABBR.put("dl.", "dual");
    ABBR.put("domest.", "domestic");
    ABBR.put("e.", "elder");
    ABBR.put("e.g.", "for example");
    ABBR.put("ea.o.", "each other");
    ABBR.put("esp.", "especially");
    ABBR.put("euphem.", "euphemism");
    ABBR.put("exagg.", "exaggeration");
    ABBR.put("f.", "for");
    ABBR.put("fam.", "family");
    ABBR.put("fem.", "feminine/female");
    ABBR.put("fig.", "figurative");
    ABBR.put("fr.", "from");
    ABBR.put("fut.", "future tense");
    ABBR.put("gen.", "general");
    ABBR.put("geom.", "geometry");
    ABBR.put("hort.", "horticulture");
    ABBR.put("hum.b.", "human being");
    ABBR.put("id.", "idiomatic");
    ABBR.put("i.e.", "that is");
    ABBR.put("inch.", "inchoative");
    ABBR.put("incl.", "including");
    ABBR.put("inf.", "informal");
    ABBR.put("int.", "interjection");
    ABBR.put("int.a", "interrogative adjective");
    ABBR.put("int.adv", "interrogative adverb");
    ABBR.put("i.o.", "in order");
    ABBR.put("joc.", "jocular");
    ABBR.put("jur.", "juridical");
    ABBR.put("Kh.g.", "Khoekhoegowab");
    ABBR.put("lang.", "language");
    ABBR.put("Lev.", "Leviticus");
    ABBR.put("ling.", "linguistics");
    ABBR.put("Lit.", "literature");
    ABBR.put("lit.", "literal");
    ABBR.put("m.", "masculine");
    ABBR.put("masc.", "masculine");
    ABBR.put("math.", "mathematics");
    ABBR.put("med.", "medical");
    ABBR.put("meteor.", "meteorology");
    ABBR.put("mil.", "military");
    ABBR.put("mod.", "modern");
    ABBR.put("mus.", "music(al)");
    ABBR.put("myth.", "mythological");
    ABBR.put("neg.", "negative");
    ABBR.put("nom.d.", "nominal designant");
    ABBR.put("o.a.", "one another");
    ABBR.put("o.'s", "one's");
    ABBR.put("o.s.", "oneself");
    ABBR.put("obl.", "oblique");
    ABBR.put("obs.", "obsolete");
    ABBR.put("obsc.", "obscene");
    ABBR.put("opp.", "opposite");
    ABBR.put("orig.", "originally");
    ABBR.put("ornith.", "ornithology");
    ABBR.put("pass.", "passive");
    ABBR.put("perf.", "perfective aspect");
    ABBR.put("pers.", "personal");
    ABBR.put("phys.", "physiology");
    ABBR.put("pl.", "plural");
    ABBR.put("poet.", "poetry");
    ABBR.put("poss.", "possessive");
    ABBR.put("poss.pr", "possessive pronoun");
    ABBR.put("postn.", "position");
    ABBR.put("postp.p.", "postpositional phrase");
    ABBR.put("pr.n.", "praise name");
    ABBR.put("pres.t.", "present tense");
    ABBR.put("prep.", "preposition");
    ABBR.put("progr.", "progressive aspect");
    ABBR.put("pron.", "pronoun");
    ABBR.put("pron.art.", "pronominally used article");
    ABBR.put("re.", "regarding");
    ABBR.put("s.one's", "someone's");
    ABBR.put("s.a.", "see also");
    ABBR.put("s.o.", "someone");
    ABBR.put("s.o.'s", "someone's");
    ABBR.put("s.th.", "something");
    ABBR.put("sg.", "singular");
    ABBR.put("spec.", "special");
    ABBR.put("suf.", "suffix");
    ABBR.put("t.p.", "tense particle");
    ABBR.put("TAM", "tense/aspect marker(s)");
    ABBR.put("tog.", "together");
    ABBR.put("trad.", "traditional");
    ABBR.put("u.", "under");
    ABBR.put("usu.", "usually");
    ABBR.put("voc.", "vocative");
    ABBR.put("vulg.", "vulgar");
    ABBR.put("w.", "with");
    ABBR.put("y.", "younger");
    ABBR.put("zool.", "zoology");
}
    
    private static final Pattern OPT = Pattern.compile("\\(([^()]*)\\)");
    private static final Pattern DIALECT_PATTERN = Pattern.compile("\\[([^\\]]+)\\]");
    private static final Pattern PRONUNC_PATTERN = Pattern.compile(
        "\\(\\s*pronunc[^)]*\\)", Pattern.CASE_INSENSITIVE);
    private static final Pattern ORIGIN_BLOCK = Pattern.compile("\\(\\s*<\\s*([^)]*?)\\)");
    private static final Pattern ORIGIN_CODE = Pattern.compile(
        "\\b(Afr\\.|E\\.|Ge\\.|He\\.|Hebr\\.)");


    // =========================================================
    // 3. Public main methods called by Khoe
    // =========================================================
    public static void processLine(String line) {
        EntryLine entry = splitMultipleEntries(line);
        finalJoin(entry);
    }

    public static void printDictionary() {
        for (WordFinal entry : dictionary) {
            System.out.println(entry);
        }
    }

    private static void printSearchResults(List<WordFinal> results) {
        if (results.isEmpty()) {
            System.out.println("Not Found");
            return;
        }

        for (WordFinal entry : results) {
            System.out.println(entry);
        }
    }


    // =========================================================
    // 4. Search and indexing methods
    // =========================================================
    /**
     * Performs a search on the dictionary based on the given query and type.
     *
     * The method selects the appropriate search function,
     * retrieves matching WordFinal objects, and prints the results.
     *
     * @param query The search string entered by the user
     * @param type  The type of search to perform
     */
    public static void search(String query, String type) {
        List<WordFinal> results;

         switch (type) {
            case "w":
                results = searchByWord(query);
                break;
            case "e":
                results = searchByDefinition(query);
                break;
            case "d":
                results = searchByDialect(query);
                break;
            case "o":
                results = searchByOrigin(query);
                break;
            case "p":
                results = searchByPartOfSpeech(query);
                break;
            default:
                results = new ArrayList<>();
                break;
        }
        printSearchResults(results);
    }

    private static List<WordFinal> searchByWord(String query) {
        List<WordFinal> results = new ArrayList<>();

        for (WordFinal entry : dictionary) {
            if (entry.getWord().contains(query)) {
                results.add(entry);
            }
        }
        return results;
    }

    private static List<WordFinal> searchByDefinition(String query) {
        List<WordFinal> results = new ArrayList<>();

        for (WordFinal entry : dictionary) {
            if (entry.getDefinition().contains(query)) {
                results.add(entry);
            }
        }
        return results;
    }

    private static List<WordFinal> searchByDialect(String query) {
        return searchIndexBySubstring(dialectIndex, query);
    }

    private static List<WordFinal> searchByOrigin(String query) {
        return searchIndexBySubstring(originIndex, query);
    }

    private static List<WordFinal> searchByPartOfSpeech(String query) {
        return searchIndexBySubstring(posIndex, query);
    }

    /**
     * Searches an index for keys that contain the given query string,
     * and returns all associated WordFinal objects.
     *
     * @param index The map storing keys (e.g. dialects, origins, POS)
     *              mapped to lists of WordFinal objects
     * @param query The substring to search for within the keys
     * @return A list of unique WordFinal objects matching the query
     */
    private static List<WordFinal> searchIndexBySubstring(
            Map<String, List<WordFinal>> index, String query) {

        Set<WordFinal> results = new LinkedHashSet<>();

        for (String key : index.keySet()) {
            if (key.contains(query)) {
                results.addAll(index.get(key));
            }
        }
        return new ArrayList<>(results);
    }

    /**
     * Adds a WordFinal entry to a specific index map.
     *
     * @param index The map storing keys (e.g. dialect, origin, POS)
     *              mapped to lists of WordFinal objects
     * @param key The category key (e.g. "Nama", "English", "noun")
     * @param entry The WordFinal object to be added
     */
    private static void addToIndex(Map<String, List<WordFinal>> index, 
        String key, WordFinal entry) {
        // If the key does not exist, create a new list for it
        if (!index.containsKey(key)) {
            index.put(key, new ArrayList<>());
        }
        index.get(key).add(entry);
    }

    /**
     * Adds a WordFinal entry to the main dictionary and updates all indexes.
     *
     * The entry is:
     *  - Stored in the main dictionary list
     *  - Indexed by dialect, origin, and part of speech for fast searching
     *
     * @param entry The WordFinal object to be added
     */
    private static void addEntry(WordFinal entry) {
        dictionary.add(entry);

        for (String dialect : entry.getDialects()) {
            addToIndex(dialectIndex, dialect, entry);
        }

        for (String origin : entry.getOrigins()) {
            addToIndex(originIndex, origin, entry);
        }

        for (String pos : entry.getPartsOfSpeech()) {
            addToIndex(posIndex, pos, entry);
        }
    }

    // =========================================================
    // 5. Build final WordFinal entries
    // =========================================================
    /**
     * Combines parsed word data (from Head) with definition data (from Tail)
     * to create final WordFinal objects for the dictionary.
     *
     * For each EntryBlock:
     *  - Iterates through all WordSpec objects
     *  - Expands each word into its final forms (WordExpanded)
     *  - Combines each expanded word with every definition
     *  - Applies double tilde (~~) substitution using the current word stem
     *  - Merges dialects from both word and definition
     *  - Creates and stores WordFinal objects
     *
     * This effectively produces all valid combinations:
     * word × expanded form × definition
     *
     * @param line The fully parsed EntryLine containing all EntryBlocks
     */
    private static void finalJoin(EntryLine line) {
        // Loop through each {head} + tail block in the line
        for (EntryBlock block : line.getEntryBlocks()) {
            Head head = block.getHead();
            Tail tail = block.getTail();

            // Loop through each word specification in the head
            for (WordSpec word : head.getWordSpecs()) {

                for (WordExpanded expWord : word.getFinalWords()) {
                    String fullWord = expWord.getFullWord();
                    String currentStem = expWord.getStem();

                    // Shared metadata from the tail
                    List<String> pos = tail.getPartsOfSpeech();
                    List<String> origins = tail.getOrigins();

                    // Dialects that belong to the word itself
                    List<String> wordDialects = expWord.getDialects();
                    
                    // Each definition creates a separate final entry
                    for (DefinitionChunk defChunk : tail.getDefinitions()) {
                        String definition = defChunk.getDefinition();

                        if (defChunk.getHasDoubleTilde()) {
                            definition = definition.replace("~~", currentStem);
                        }

                        // Combine dialects from word and definition
                        List<String> defDialects = defChunk.getDialects();
                        List<String> combinedDialects = combineDialects(wordDialects, defDialects);

                        WordFinal finalWord = new WordFinal(fullWord, 
                            combinedDialects, 
                            pos, 
                            origins, 
                            definition);
                        addEntry(finalWord);
                
                    }
                }

            }

        }
    }    


    // =========================================================
    // 6. Full line / entry splitting
    // =========================================================

    /**
     * Splits a raw dictionary line into multiple EntryBlock objects.
     *
     * Each EntryBlock consists of:
     *  - A head section (inside { })
     *  - A tail section (text after the head until the next { or end of line)
     *
     * The method:
     * 1. Extracts each {head} and its corresponding tail
     * 2. Parses head into WordSpec objects
     * 3. Parses tail into definitions, POS, origins, etc.
     * 4. Stores all blocks in an EntryLine
     * 5. Identifies the first word (needed for tilde expansion)
     * 6. Expands all words (handles optional parts and ~ rules)
     *
     * @param line The raw input line from the dictionary
     * @return EntryLine containing all parsed EntryBlocks
     */
    private static EntryLine splitMultipleEntries(String line) {
        ArrayList<EntryBlock> blocks = new ArrayList<>();
        int i = 0;

        // Loop through the line to extract all {head} ... tail sections
        while (i < line.length()) {
            int openIndex = line.indexOf('{', i);
            if (openIndex == -1) {
                break;
            }

            int closeIndex = line.indexOf('}', openIndex);
            if (closeIndex == -1) {
                break;
            }
            
            String rawHead = line.substring(openIndex, closeIndex + 1);

            // Find where the next block starts to determine the tail boundary
            int nextOpenIndex = line.indexOf('{', closeIndex + 1);
            String rawTail = "";

            if (nextOpenIndex == -1) {
                // if last block, tail goes until end of line
                rawTail = line.substring(closeIndex + 1).trim();
                i = line.length();
            } else {
                // Tail is between current } and next {
                rawTail = line.substring(closeIndex + 1, nextOpenIndex).trim();
                i = nextOpenIndex;
            }

            // Split head into individual word specifications
            List<String> rawWords = new ArrayList<>();
            rawWords = splitHead(rawHead);

            // Parse each word into a WordSpec object
            List<WordSpec> headsWS = new ArrayList<>();
            for (int k = 0; k < rawWords.size(); k++) {
                WordSpec wordSpec =  parseWord(rawWords.get(k));
                headsWS.add(wordSpec);
            }
            Head headObject = new Head(rawHead);

            // Parse tail into structured data
            Tail tailObject = new Tail(rawTail);
            tailObject = parseTail(rawTail);
            
            headObject.setWordSpecs(headsWS);
            EntryBlock entryBlock = new EntryBlock(rawHead, rawTail);
            entryBlock.setHead(headObject);
            entryBlock.setTail(tailObject);
            blocks.add(entryBlock);
        }
        EntryLine entryLine = new EntryLine(line, blocks);

        // Extract first word information (needed for tilde (~) expansion)
        if (!blocks.isEmpty()) {
            EntryBlock firstBlock = blocks.get(0);
            Head firstHead = firstBlock.getHead();

            if (firstHead != null 
                && firstHead.getWordSpecs() != null 
                && !firstHead.getWordSpecs().isEmpty()) {
                WordSpec firstWord = firstHead.getWordSpecs().get(0);
                entryLine.setFirstWordSpec(firstWord);

                if (firstWord.hasOptionalParts()) {
                    entryLine.setFirstStemOptions(firstWord.getStemOptions());
                    entryLine.setFirstStemLongest(longestString(firstWord.getStemOptions()));
                }
            }
        }
        // Expand all words and apply tilde rules
        getWordsFinal(entryLine);
        return entryLine;
    }


    // =========================================================
    // 7. Word expansion / tilde handling
    // =========================================================

    /**
     * Determines how to expand all words in an EntryLine based on the first word.
     *
     * This method:
     * 1. Extracts the first word
     * 2. Determines possible stems to use for '~' expansion
     * 3. Determines what single '~' in definitions should become
     * 4. Calls expandWords(...) to generate all WordExpanded objects
     *
     * @param line The EntryLine containing parsed dictionary blocks
     */
    private static void getWordsFinal(EntryLine line) {
        WordSpec firstWord = line.getFirstWordSpec();
        List<String> firstStems = new ArrayList<>();
        String definitionReplacement;

        if (firstWord.hasOptionalParts()) {
            firstStems.addAll(firstWord.getStemOptions());
            definitionReplacement = line.getFirstStemLongest();
        } else {
            firstStems.add(firstWord.getStemTemplate());
            definitionReplacement = firstWord.getStemTemplate();
        }
        expandWords(line, firstStems, definitionReplacement);
    }

    /**
     * Expands every WordSpec in an EntryLine into its final word forms.
     *
     * Words with a '~' prefix use the first word's possible stems.
     * Words without a '~' prefix are expanded normally.
     *
     * After word expansion, single '~' symbols in definitions are replaced
     * using the correct first-stem replacement.
     *
     * @param line The EntryLine containing parsed blocks
     * @param firstStems Possible first-word stems used for '~' word expansion
     * @param definitionReplacement Replacement for single '~' in definitions
     */
    private static void expandWords(
            EntryLine line,
            List<String> firstStems,
            String definitionReplacement) {
        
        for (EntryBlock block : line.getEntryBlocks()) {
            Head head = block.getHead();
            Tail tail = block.getTail();

            for (WordSpec word : head.getWordSpecs()) {
                List<WordExpanded> finalWords = new ArrayList<>();

                // '~' at the start of a word uses the first word's stem(s)
                if (word.hasSingleTildePrefix()) {
                    addTildeWordForms(word, firstStems, finalWords);
                } else {
                    addNormalWordForms(word, finalWords);
                }
                word.setFinalWords(finalWords);
            }
            replaceSingleTildeInDefinitions(tail, definitionReplacement);
        }
    }

    /**
     * Creates expanded word forms for words that begin with a single '~'.
     *
     * The '~' is replaced by each possible first-word stem. If the current word
     * also has optional parts, every first stem is combined with every option.
     *
     * @param word The WordSpec being expanded
     * @param firstStems Possible first-word stems used to replace '~'
     * @param finalWords The list where expanded WordExpanded objects are stored
     */
    private static void addTildeWordForms(
            WordSpec word,
            List<String> firstStems,
            List<WordExpanded> finalWords) {

        for (String firstStem : firstStems) {
            if (word.hasOptionalParts()) {
                // Create every combination: first stem + each optional form
                for (String option : word.getStemOptions()) {
                    addExpandedWord(word, firstStem + option, finalWords);
                }
            } else {
                addExpandedWord(word, firstStem + word.getStemTemplate(), finalWords);
            }
        }
    }

    /**
     * Creates expanded word forms for words that do NOT begin with '~'.
     *
     * @param word The WordSpec being expanded
     * @param finalWords The list where expanded WordExpanded objects are stored
     */
    private static void addNormalWordForms(
            WordSpec word,
            List<WordExpanded> finalWords) {

        if (word.hasOptionalParts()) {
            for (String option : word.getStemOptions()) {
                addExpandedWord(word, option, finalWords);
            }
        } else {
            addExpandedWord(word, word.getStemTemplate(), finalWords);
        }
    }

    /**
     * Creates a WordExpanded object from a given stem and adds it to the list.
     *
     * @param word The original WordSpec containing suffix and dialect information
     * @param stem The base stem used to form the word
     * @param finalWords The list where the new WordExpanded object is stored
     */
    private static void addExpandedWord(
            WordSpec word,
            String stem,
            List<WordExpanded> finalWords) {

        WordExpanded expandedWord = new WordExpanded(
            stem,
            stem + word.getSuffix(),
            word.getDialects()
        );

        finalWords.add(expandedWord);
    }

    /**
     * Replaces single '~' symbols in definitions with the provided replacement.
     *
     * @param tail The Tail containing definition chunks
     * @param replacement The string to replace single '~' with
     */
    private static void replaceSingleTildeInDefinitions(
            Tail tail,
            String replacement) {

        for (DefinitionChunk definitionChunk : tail.getDefinitions()) {
            if (definitionChunk.getHasSingleTilde()) {
                String replacedDefinition = definitionChunk.getDefinition().replaceAll(
                    "(?<!~)~(?!~)", // match only single '~'
                    replacement
                );

                definitionChunk.setDefinition(replacedDefinition);
            }
        }
    }

    /**
     * Finds and returns the longest string in a list.
     *
     * @param list The list of strings to search
     * @return The longest string in the list, or null if the list is null or empty
     */
    private static String longestString(List<String> list) {
        String longest = list.get(0);
        for (String current : list) {
            if (current.length() > longest.length()) {
                longest = current;
            }
        }
        return longest;
    }


    // =========================================================
    // 8. Head / word parsing
    // =========================================================
    /**
     * Splits the head section of an entry into individual word specifications.
     *
     * The head is enclosed in { } and may contain multiple entries separated by ',' or ';'
     * This method ensures splitting only occurs at the top
     * level (i.e. not inside brackets such as < >, ( ), or [ ]).
     *
     * @param head The raw head string including enclosing braces
     * @return A list of individual word specification strings
     */
    private static ArrayList<String> splitHead(String head) {
        ArrayList<String> parts = new ArrayList<>();
        int segmentStart = 1;
        int angleDepth = 0; 
        int squareDepth = 0; 
        int roundDepth = 0; 

        for (int i = 0; i < head.length() - 1; i++) {
            char currentChar = head.charAt(i);

             // Track nesting depth of different bracket types
            if (currentChar == '(') {
                roundDepth++;
            } else if (currentChar == ')') {
                 roundDepth--;
            } else if (currentChar == '[') {
                squareDepth++;
            } else if (currentChar == ']') {
                squareDepth--;
            } else if (currentChar == '<') {
                angleDepth++;
            } else if (currentChar == '>') {
                angleDepth--;
            }

            // Split only when at top level (not inside any brackets)
            if ((currentChar == ';' || currentChar == ',') 
                && (angleDepth == 0 && roundDepth == 0 && squareDepth == 0)) {
                
                String part = head.substring(segmentStart, i);
                parts.add(part.trim());
                segmentStart = i + 1;
            }
        }
        // Add final segment (excluding closing '}')
        parts.add(head.substring(segmentStart, head.length() - 1).trim());
        return parts;
    }

    /**
     * Parses a raw word specification from the head section into a WordSpec object.
     *
     * @param raw The raw word specification string
     * @return A WordSpec object containing the parsed components of the word
     */
    private static WordSpec parseWord(String raw) {
        WordSpec wordSpec = new WordSpec(raw);
        String suffix = "";
        String wordText = raw.trim();
        
        // get dialect info
        Matcher dialectMatcher = DIALECT_PATTERN.matcher(wordText);
       
        if (!wordText.isEmpty() && dialectMatcher.find()) {
            String dialect = dialectMatcher.group(1); // extracts text inside [ ]
            List<String> dialects = new ArrayList<>();
            String[] dialectCodes = dialect.split(",");
            for (String part : dialectCodes) {
                String code = part.trim();
                if (VALID_DIALECTS.contains(code)) {
                    String codeExpanded = expandDialect(code);
                    dialects.add(codeExpanded);
                }
            }
            wordSpec.setDialectCodes(dialects);

            // remove dialect block from word text 
            int index = wordText.indexOf('[');
            wordText = wordText.substring(0, index).trim();
        }
        
        // remove outer brackets indicating uncommon forms, e.g. "(<word>s)"
        if (!wordText.isEmpty() && wordText.charAt(0) == '(' 
            && wordText.charAt(wordText.length() - 1) == ')') {
            wordText = wordText.substring(1, wordText.length() - 1);   
        }
        wordText = wordText.trim();

        // detect tilde prefix (~) which refers to previous stem
        if (!wordText.isEmpty() && wordText.charAt(0) == '~') {
            wordSpec.setSingleTildePrefix(true);
            wordText = wordText.substring(1);
        }

        int start = wordText.indexOf('<');
        int end = wordText.indexOf('>');

        // suffix is any text after '>' in the word
        if (!wordText.isEmpty() &&  wordText.charAt(wordText.length() - 1) != '>') {
            suffix = wordText.substring(end + 1);
            wordSpec.setSuffix(suffix); 
        } else {
            wordSpec.setSuffix("");
        }

        // extract the stem template between < and >
        String stem = wordText.substring(start + 1, end);
        wordSpec.setStemTemplate(stem);

        // generate all combinations if the stem contains optional parts
        if (stem.contains("(") && stem.contains(")")) {
            List<String> optionals = generateOptionalForms(stem);
            wordSpec.setHasOptionalParts(true);
            wordSpec.setStemOptions(optionals);
        }
        return wordSpec; 
    }

    /**
     * Generates all possible forms of a string containing optional parts in parentheses.
     *
     * @param input The string containing optional parts in parentheses
     * @return A list of all possible expanded forms
     */
    private static List<String> generateOptionalForms(String input) {
        List<String> optionals = new ArrayList<>();
        optionals.add(input);
        
        while (true) {
            boolean expanded = false; // tracks if any optional part was found this round
            List<String> nextForms = new ArrayList<>();

            for (String currentForm : optionals) {
                // Matches optional parts in parentheses, capturing the text inside
                Matcher optionalMatcher = OPT.matcher(currentForm);
                if (optionalMatcher.find()) {
                    expanded = true;
                    String optionalText = optionalMatcher.group(1);  
                    int startIndex = optionalMatcher.start();        
                    int endIndex = optionalMatcher.end();   

                    String prefix = currentForm.substring(0, startIndex);
                    String suffix = currentForm.substring(endIndex);

                    // Form without the optional part
                    nextForms.add(prefix + suffix);
                    
                    // Form including the optional part
                    nextForms.add(prefix + optionalText + suffix);
                } else {
                    nextForms.add(currentForm);
                }
            }
            optionals = nextForms;
            if (!expanded) { // stop when no more optional parts remain
                break;
            }
        }
        return optionals;
    }

   
    // =========================================================
    // 9. Tail parsing
    // =========================================================
    /**
     * Parses the tail section of an entry into a Tail object.
     *
     * The tail contains the information after the head word, such as parts of speech,
     * origins, definitions, and definition-specific dialects. This method removes
     * pronunciation blocks, extracts parts of speech and origins, cleans the remaining
     * definition text, splits multiple numbered definitions, and creates DefinitionChunk
     * objects for each definition.
     *
     * @param raw The raw tail text after the closing curly bracket
     * @return A Tail object containing the parsed parts of speech, origins, and definitions
     */
    private static Tail parseTail(String raw) {
        Tail tailObject = new Tail(raw);
        String tailText = raw;

        // Remove edge punctuation, pronunciation guides, and extra spacing
        tailText = trimString(tailText);
        tailText = PRONUNC_PATTERN.matcher(tailText).replaceAll("").trim();
        tailText = tailText.replaceAll("\\s{2,}", " ").trim();
        
        String[] tokens = tailText.split("\\s+");
        List<String> partsOfSpeech = new ArrayList<>();
        int i = 0;
        
        while (i < tokens.length) {
            String cleanedToken = trimString(tokens[i]);

            // Find and extract parts of speech from tokens
            if (POS.contains(cleanedToken)) {
                String posExpanded = expandPOS(cleanedToken);
                partsOfSpeech.add(posExpanded);
                tokens[i] = "";
            }
            i++;
        }
        
        tailObject.setPartsOfSpeech(partsOfSpeech);
        StringBuilder definitionBuilder = new StringBuilder();

        // Rebuild definition text without POS tokens
        for (int j = 0; j < tokens.length; j++) {
            definitionBuilder.append(tokens[j]).append(" ");
        }
        tailText = (definitionBuilder.toString().trim()).replaceAll("\\s{2,}", " ");
        
        // Extract origin languages, then remove origin blocks from the text
        List<String> origins = new ArrayList<>();
        origins = extractOrigins(tailText);
        tailText = tailText.replaceAll("\\(\\s*<\\s*([^)]*?)\\)", "").trim();
        tailText = tailText.replaceAll("\\s+([,;:])", "$1");
        tailText = tailText.replaceAll("\\s{2,}", " ").trim();
        tailObject.setOrigins(origins);
       
        // Split into individual definitions (handles numbered definitions)
        List<String> definitionTexts = new ArrayList<>();
        definitionTexts = splitDefinitions(tailText);
        List<DefinitionChunk> definitionChunks = new ArrayList<>();
        
        // Convert each definition into a structured DefinitionChunk
        for (String definitionText : definitionTexts) {
            DefinitionChunk defChunk =  getDefinitionChunk(definitionText);
            definitionChunks.add(defChunk);
        }
        tailObject.setDefinitions(definitionChunks);

        return tailObject;
    }

    /**
     * Creates a DefinitionChunk from a raw definition string.
     *
     * This method extracts dialect information from square brackets (e.g. [N,D]),
     * removes valid dialect from the definition, and keeps any non-dialect
     * bracketed text. It also detects single (~) and double (~~) tilde usage,
     * expands abbreviations, and stores the cleaned definition and metadata
     * in a DefinitionChunk object.
     *
     * @param raw The raw definition string to process
     * @return A DefinitionChunk containing the cleaned definition, dialects, and tilde information
     */
    private static DefinitionChunk getDefinitionChunk(String raw) {
        DefinitionChunk definitionChunk = new DefinitionChunk();
        String definition = raw.trim();

        // Match square bracket sections like [N,D]
        Matcher dialectMatcher = DIALECT_PATTERN.matcher(definition);

        List<String> dialects = new ArrayList<>();
        StringBuilder cleanedDefinition = new StringBuilder();
        int lastEnd = 0;

        while (dialectMatcher.find()) {
            String inside = dialectMatcher.group(1); // text inside brackets
            String[] parts = inside.split(",");
            boolean allValidDialects = true;

            for (String part : parts) {
                String dialectCode = part.trim();

                if (VALID_DIALECTS.contains(dialectCode)) {
                    dialects.add(expandDialect(dialectCode)); // add expanded dialect
                } else {
                    allValidDialects = false;
                }
            }

            // Add text before the current bracket
            cleanedDefinition.append(definition, lastEnd, dialectMatcher.start());

            // Keep bracket if it is not a valid dialect block
            if (!allValidDialects) {
                cleanedDefinition.append(dialectMatcher.group());
            }
            lastEnd = dialectMatcher.end(); // move past this bracket
        }

        cleanedDefinition.append(definition.substring(lastEnd));
        definition = cleanedDefinition.toString().trim();

        // Detect tilde usage
        boolean hasDouble = definition.contains("~~");
        boolean hasSingle = Pattern.compile("(?<!~)~(?!~)").matcher(definition).find();

        definitionChunk.setHasDoubleTilde(hasDouble);
        definitionChunk.setHasSingleTilde(hasSingle);

        definitionChunk.setDialects(dialects);

        // Clean and expand definition text
        String finalDefinition = trimString(definition);
        finalDefinition = expandAbbreviations(finalDefinition, ABBR);
        definitionChunk.setDefinition(finalDefinition);

        return definitionChunk;
    }

    /**
     * Splits a definition string into multiple definitions based on numbered markers
     * such as "1. ... 2. ... 3. ...".
     *
     * The method only performs splitting if the definition starts with "1" and follows
     * a sequential numbering pattern (1, 2, 3, ...) to prevent accidental splitting
     * when numbers appear in normal text.
     *
     * @param defSection The full definition text to be processed
     * @return A list of definitions, or a single-item list if no valid numbering is found
     */
    private static List<String> splitDefinitions(String defSection) {
        List<String> definitions = new ArrayList<>();
        String section = defSection.trim();

        // finds definition numbers
        Pattern defMark = Pattern.compile("(?:(?<=\\s)|^)(\\d+)\\.?(?=\\s)"); 
        Matcher markerMatcher = defMark.matcher(section);

        // Store positions and values of detected number markers
        List<Integer> markerPositions = new ArrayList<>(); 
        List<Integer> markerNumbers = new ArrayList<>();

        while (markerMatcher.find()) { 
            markerPositions.add(markerMatcher.start());
            markerNumbers.add(Integer.parseInt(markerMatcher.group(1)));
        }

        // If no valid numbering starting with 1 at the beginning, treat as a single definition
        if (markerPositions.isEmpty() || markerPositions.get(0) != 0 || markerNumbers.get(0) != 1) {
            definitions.add(section.trim());
            return definitions;
        }

        // Keep only correctly ordered sequential numbers 
        List<Integer> validMarkerPositions = new ArrayList<>();
        int expectedNumber = 1;

        for (int i = 0; i < markerPositions.size(); i++) {
            if (markerNumbers.get(i) == expectedNumber) {
                validMarkerPositions.add(markerPositions.get(i));
                expectedNumber++;
            }
        }

        // Extracts definition text between each valid marker
        for (int i = 0; i < validMarkerPositions.size(); i++) {
            int startPos = validMarkerPositions.get(i);

            // skip definition marker to the start of the definition text
            Matcher currentMarker = defMark.matcher(section);
            currentMarker.find(startPos);
            int contentStart = currentMarker.end();

            int endPos;

            // if there is another definition
            if (i + 1 < validMarkerPositions.size()) {
                endPos = validMarkerPositions.get(i + 1); // stop at next definition marker
            } else {
                endPos = section.length(); // last definition stops at the end of the string
            }

            String definitionText = section.substring(contentStart, endPos).trim();
            definitions.add(definitionText);
        }
        return definitions;
    }

    /**
     * Extracts language origins from a definition string.
     *
     * This method searches for origin blocks of the form (< ... ) within the definition,
     * then identifies any recognised origin codes inside those blocks (e.g. "E.", "Afr.").
     * Each code is expanded to its full language name (e.g. "English", "Afrikaans")
     * and added to the result list, ensuring no duplicates are included.
     *
     * @param definition The definition string that may contain origin information
     * @return A list of unique expanded origin names found in the definition
     */
    private static List<String> extractOrigins(String definition) {
        List<String> origins = new ArrayList<>();
        Matcher blockMatcher = ORIGIN_BLOCK.matcher(definition); // looks for origin blocks (<...)

        while (blockMatcher.find()) {
            String inside = blockMatcher.group(1); // extract info inside the block
            
            Matcher codeMatcher = ORIGIN_CODE.matcher(inside); // looks for origin codes

            while (codeMatcher.find()) {
                String code = expandOrigin(codeMatcher.group(1)); // extracts the origin code
                
                if (!origins.contains(code)) {
                    origins.add(code);
                }
            }
        }
        return origins;
    }
 
    // =========================================================
    // 10. Expansion helpers
    // =========================================================

    private static String expandOrigin(String originCode) {
        switch (originCode) {
            case "Afr.": return "Afrikaans";
            case "E.": return "English";
            case "Ge.": return "German";
            case "He.": return "Otjiherero";
            case "Hebr.": return "Hebrew";
            default: return originCode;
        }
    }
 
    /**
     * Expands all abbreviations in a definition string using the abbreviation map.
     * Abbreviations are processed from longest to shortest to prevent partial replacements
     * 
     * @param text     The definition text that may contain abbreviations
     * @param abbrMap  A map of abbreviations to their full expanded forms
     * @return         The definition with all abbreviations expanded
     */
    private static String expandAbbreviations(String text, Map<String, String> abbrMap) {
        List<String> keys = new ArrayList<>(abbrMap.keySet());
        keys.sort((a, b) -> b.length() - a.length());

        for (String abbr : keys) {
            text = text.replace(abbr, abbrMap.get(abbr));
            //text = text.replaceAll("\\b" + Pattern.quote(abbr), abbrMap.get(abbr));
        }
        return text;
    }
    
    private static String expandPOS(String pos) {
        switch (pos) {
            case "a.": return "adjective";
            case "abbr.": return "abbreviation";
            case "adv.": return "adverb";
            case "conj.": return "conjunction";
            case "excl.": return "exclusive";
            case "ideo.": return "ideophone";
            case "n.": return "noun";
            case "num.": return "numeral";
            case "o.m.": return "object marker";
            case "postp.": return "postposition";
            case "v.": return "verb";
            case "v.i": return "intransitive verb";
            case "v.refl": return "reflexive verb";
            case "v.recip": return "reciprocal verb";
            case "v.t": return "transitive verb";
            case "v.t/i": return "transitive/intransitive verb";
            case "v.t.stat": return "stative transitive verb";
            case "v.tt.stat": return "stative ditransitive verb";
            case "v.i.stat": return "stative intransitive verb";
            case "v.tt": return "ditransitive verb";
            default: return pos;
        }
    }
   
    private static String expandDialect(String dialect) {
        switch (dialect) {
            case "N": return "Nama";
            case "D": return "Damara";
            case "T": return "Topnaar";
            case "Hm": return "Haiǁom";
            case "Bz": return "Bondelzwarts";
            case "S": return "Sesfontein dialect(s)";
            case "Namid": return "Namidama";
            case "ǂA": return "ǂĀkhoe";
            case "ǂD": return "ǂAodama";
            case "V": return "Vaalgras Nama";
            default: return dialect;
        }
    }

    // =========================================================
    // 11. Small utility helpers
    // =========================================================
    /**
     * Removes unwanted leading and trailing punctuation and whitespace
     * from a definition string.
     *
     * @param input The raw definition string to clean
     * @return A cleaned definition string with no surrounding punctuation or spaces
     */
    private static String trimString(String input) {
        input = input.trim();

        // Continuously remove unwanted punctuation from the beginning of the string until clean
        while (!input.isEmpty() 
            && (input.startsWith(":") || input.startsWith(";") || input.startsWith(","))) {
            input = input.substring(1).trim();
        }

        // Remove unwanted punctuation from the end of the string
        while (!input.isEmpty() 
            && (input.endsWith(":") || input.endsWith(";") || input.endsWith(","))) {
            input = input.substring(0, input.length() - 1).trim();
        }
        return input;
    }

    /**
     * Combines two lists of dialects into a single list without duplicates.
     *
     * @param wordDialects List of dialects associated with the word
     * @param definitionDialects List of dialects associated with the definition
     * @return A combined list of unique dialects
     */
    private static List<String> combineDialects(List<String> wordDialects, 
        List<String> definitionDialects) {
        List<String> result = new ArrayList<>(wordDialects);
        for (String dialect : definitionDialects) {
            if (!result.contains(dialect)) {
                result.add(dialect);
            }
        }
        return result;
    }
 
}

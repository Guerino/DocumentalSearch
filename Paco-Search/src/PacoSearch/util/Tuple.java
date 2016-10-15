package PacoSearch.util;

/**
 *
 * @author
 * Guerino
 */
 public class Tuple {
        private String word;
        private int docId;
        private int tf;

        public Tuple() {
         this.word = null;
         this.docId = 0;
         this.tf = 0;
        }       
        
        public Tuple(String word, int docId, int tf) {
            this.word = word;
            this.docId = docId;
            this.tf = tf;
        } 

        public String getWord() {
            return word;
        }

        public int getDocId() {
            return docId;
        }

        public int getTf() {
            return tf;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public void setDocId(int docId) {
            this.docId = docId;
        }

        public void setTf(int tf) {
            this.tf = tf;
        }     

        @Override
        public String toString() {
            return "Tuple{" + "word=" + word + ", docId=" + docId + ", tf=" + tf + '}';
        }

   }
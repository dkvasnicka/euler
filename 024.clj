
(defn factorial [n]
  (reduce * (range 1 (inc n))))

(defn nth-lex-permutation [digits idx & {:keys [out] :or {out []}}]
  (let [dcount (count digits)]
    (if (= 1 dcount)
      (conj out (first digits))
      (let [fac (factorial (dec dcount))
            digit-idx (quot idx fac)
            digit (nth digits digit-idx)]
        (nth-lex-permutation (vec (concat (subvec digits 0 digit-idx) 
                                          (subvec digits (inc digit-idx))))
                             (mod idx fac)
                             :out (conj out digit))))))

(nth-lex-permutation (vec (range 10)) 999999)

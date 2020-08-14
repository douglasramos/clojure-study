(ns clojure-study.exercism.armstrong-numbers)

(defn power
  "power operation"
  [x n]
  (reduce * (repeat n x)))

(defn number->digits-list
  [number]
  (loop [n number digits []]
    (if (zero? n)
      digits
      (recur (quot n 10) (cons (mod n 10) digits)))))

(defn armstrong-number?
  [n]
  (let [result
        (let [digits (number->digits-list n)]
          (reduce + (map
                      #(power % (count digits))
                      digits)))]
    (= result n)))


(armstrong-number? 9)

;; another implementaion
;; number to string. string to seq of charecters.
;; seq of charecters to seq of strings (each string is one charecter)
;; seq of string to seq of ints (through read-string)
;; the rest is legend
(defn armstrong?
  [n]
  (->> (str n)
       seq
       (map str)
       (map read-string)
       (map #(reduce * (repeat (count (str n)) %)))
       (reduce +)
       (= n)))

;; yet another implmenation

(defn armstrong2? [n]
  (let [digits (->> n str (map (fn [^Integer x] (Character/digit x 10))))]
    (->> digits
         (map #(Math/pow % (count digits)))
         (apply +)
         (== n))))

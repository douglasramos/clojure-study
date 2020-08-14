(ns clojure-study.for-the-brave.chapter-3.body-parts)
;; Definitions

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])


(def generic-asym-hobbit-body-parts [{:name "head" :size 3}
                                     {:name "1th-eye" :size 1}
                                     {:name "1th-ear" :size 1}
                                     {:name "1th-ear" :size 1}
                                     {:name "mouth" :size 1}
                                     {:name "nose" :size 1}
                                     {:name "neck" :size 2}
                                     {:name "1th-shoulder" :size 3}
                                     {:name "1th-upper-arm" :size 3}
                                     {:name "chest" :size 10}
                                     {:name "back" :size 10}
                                     {:name "left-forearm" :size 3}
                                     {:name "abdomen" :size 6}
                                     {:name "1th-kidney" :size 1}
                                     {:name "1th-hand" :size 2}
                                     {:name "1th-knee" :size 2}
                                     {:name "1th-thigh" :size 4}
                                     {:name "1th-lower-leg" :size 3}
                                     {:name "1th-achilles" :size 1}
                                     {:name "1th-foot" :size 2}])

(defn sort-map-value [map-to-sort]
  (into (sorted-map-by (fn [key1 key2]
                         (compare [(get map-to-sort key2) key2]
                                  [(get map-to-sort key1) key1])))
        map-to-sort))


(defn sort-parts-by-value [parts]
  (sort-by :name parts))

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

;;;; generic sym

(defn nth-new-part [part nth]
  (when (re-find #"^1th-" (:name part))
    {:name (clojure.string/replace (:name part) #"^1th-" (str nth "th-"))
     :size (:size part)}))

(defn generic-matching-part
  [part quantity]
  (remove nil? (reduce #(conj %1 (nth-new-part part %2)) #{} (range 2 (inc quantity)))))


(defn generic-symetrize-body-parts
  "A simpler version of the previous funtion"
  [asym-body-parts quantity]
  (reduce #(into %1 (cons %2 (generic-matching-part %2 quantity)))
          [] asym-body-parts))

(sort-parts-by-value (generic-symetrize-body-parts generic-asym-hobbit-body-parts 5))
;;;;

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))

;; Actions
(symmetrize-body-parts asym-hobbit-body-parts)


(defn better-symetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          [] asym-body-parts))

(better-symetrize-body-parts asym-hobbit-body-parts)

(defn better-symetrize-body-parts-2
  "A simpler version of the previous funtion"
  [asym-body-parts]
  (reduce #(into %1 (set [%2 (matching-part %2)]))
          [] asym-body-parts))

(better-symetrize-body-parts-2 asym-hobbit-body-parts)
















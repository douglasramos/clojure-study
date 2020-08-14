(ns clojure-study.datomic
  (:require [datomic.client.api :as d]))

;; Conenction to database
(def conn (let [client (let [cfg {:server-type :peer-server
                                  :access-key  "datomic-db"
                                  :secret      "datomic-db"
                                  :endpoint    "localhost:8998"}]
                         (d/client cfg))]
            (d/connect client {:db-name "study"})))

;; movie entities schema
(def movie-schema [{:db/ident       :movie/title
                    :db/valueType   :db.type/string
                    :db/cardinality :db.cardinality/one
                    :db/doc         "The title of the movie"}

                   {:db/ident       :movie/genre
                    :db/valueType   :db.type/string
                    :db/cardinality :db.cardinality/one
                    :db/doc         "The genre of the movie"}

                   {:db/ident       :movie/release-year
                    :db/valueType   :db.type/long
                    :db/cardinality :db.cardinality/one
                    :db/doc         "The year the movie was released in theaters"}])

;; transact schema
(d/transact conn {:tx-data movie-schema})

;; first movies
(def first-movies [{:movie/title        "The Goonies"
                    :movie/genre        "action/adventure"
                    :movie/release-year 1985}
                   {:movie/title        "Commando"
                    :movie/genre        "action/adventure"
                    :movie/release-year 1985}
                   {:movie/title        "Repo Man"
                    :movie/genre        "punk dystopia"
                    :movie/release-year 1984}])

;; transact data
(d/transact conn {:tx-data first-movies})

;; get the db as a value (from that a same query always will return the
;; same result as long as we dont change the db variable

(def db (d/db conn))

;; query: find me the ids of all entities which have an attribute called :movie/title

(def all-movies-q '[:find ?e
                    :where [?e :movie/title]])

;; execute the query
(d/q all-movies-q db)

;; query: find all movie titles from any entity that has an attribute :movie/title and
;; assign the title to a logic variable called ?movie-title
(def all-titles-q '[:find ?movie-title
                    :where [_ :movie/title ?movie-title]])

;; execute the query
(d/q all-titles-q db)





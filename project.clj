(let [properties (select-keys (into {} (System/getProperties))
                              ["os.arch" "os.name"])
      platform (apply format "%s (%s)" (vals properties))
      phantomjs (if (re-find #"(?i)^Windows" platform) "phantomjs.cmd" "phantomjs")]
  (defproject ttt "0.1.0-SNAPSHOT"
    :description "FIXME: write description"
    :url "http://example.com/FIXME"
    :license {:name "Eclipse Public License"
              :url  "http://www.eclipse.org/legal/epl-v10.html"}

    :dependencies [[org.clojure/clojure "1.6.0"]]

    :profiles {:dev {:dependencies [[org.clojure/clojurescript "0.0-2234"]
                                    [speclj "3.1.0"]]}}
    :plugins [[speclj "3.1.0"]
              [lein-cljsbuild "1.0.3"]]

    :cljsbuild {:builds        {:dev  {:source-paths   ["src/cljs" "spec/cljs"]
                                       :compiler       {:output-to     "js/ttt_dev.js"
                                                        :optimizations :whitespace
                                                        :pretty-print  true}
                                       :notify-command [~phantomjs "bin/speclj" "js/ttt_dev.js"]}

                                :prod {:source-paths ["src/cljs"]
                                       :compiler     {:output-to     "js/ttt.js"
                                                      :optimizations :simple}}}
                :test-commands {"test" [~phantomjs "bin/speclj" "js/ttt_dev.js"]}}

    :source-paths ["src/clj" "src/cljs"]
    :clean-targets ["js" "target"]
    :test-paths ["spec/clj"]))

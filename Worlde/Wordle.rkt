
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname hw9-problem1) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/batch-io)
(require 2htdp/image)
(require 2htdp/universe)

; Data (General)
; --------------
;
; - Pair 
;   A generalized definition for associating two arbitrary
;   data types; this is used both for LetterStatusPair and
;   in helping to visualize elements of the game by
;   pairing up a game object, such as a guesed letter, with
;   a function used to visualize that type of object
;

(define-struct pair [first second])

; A [Pair X Y] is a (make-pair X Y)
; Interpretation: a pairing of two values


;
; Data (Letters)
; --------------
;
; - LetterStatus 
;   A categorization of scored letters
;
; - LetterStatusPair 
;   An association between a specific letter and its scored
;   status; updated to use Pair
;
; - VizPair
;   An association between a value and a function that can
;   produce an image for it
;


; A LetterStatus (LS) is one of:
; - "wrong"
; - "misplaced"
; - "right"
; Interpretation: status of a guessed letter

(define LS-WRONG "wrong")
(define LS-MISPLACED "misplaced")
(define LS-RIGHT "right")


; A LetterStatusPair (LSP) is a [Pair 1String LetterStatus]
; Interpretation: a guess letter and its associated status


; A [VizPair X] is a [Pair X [X -> Image]]
; Interpretation: a pairing of a value with a function that
; produces a visualization of it


;
; Constants (Visualization)
; -------------------------
;
; - Game background (BG-COLOR)
;
; - Current/upcoming guess border (BORDER-COLOR)
;
; - Current/upcoming guess background (GUESS-COLOR)
;
; - Letter size (LT-SIZE)
;
; - Buffer space between game objects (GAP)
;

(define BG-COLOR "white")
(define BORDER-COLOR "dimgray")
(define GUESS-COLOR "black")

(define LT-SIZE 64)

(define GAP (square 5 "solid" BG-COLOR))

;
; Functions (General)
; -------------------
;
; - mymap2 
;   Abstraction for capturing the result of applying a
;   function to the elements from two parallel lists
;


; mymap2 : (X Y Z) [List-of X] [List-of Y] [X Y -> Z] -> [List-of Z]
; produces a result list by applying the supplied function
; to parallel elements from the supplied lists (until either
; is empty)

(check-expect
 (mymap2 '() '() *)
 '())

(check-expect
 (mymap2 (list 2 3) (list 4) *)
 (list 8))

(check-expect
 (mymap2 (list "a" "b")
         (list "1" "2" "3")
         string-append)
 (list "a1" "b2"))

(check-expect
 (mymap2 (list "a" "c")
         (list (list "x") (list 3 1 4))
         (λ (s l) (string-append
                   s
                   "-"
                   (number->string (length l)))))
 (list "a-1" "c-3"))

(define (mymap2 l1 l2 f)
  (cond
    [(or (empty? l1) (empty? l2)) '()]
    [(and (cons? l1) (cons? l2))
     (cons (f (first l1) (first l2))
           (mymap2 (rest l1) (rest l2) f))]))
  

; Functions (Visualizing Letters)
; -------------------------------
;
; - boxed-letter 
;   Abstraction for drawing a letter over a box; uses an
;   updated test to reflect that a "blank" box for guesses
;   is now local to a helper
;
; - guess-letter->image 
;   Uses boxed-letter to provide a simple way to visualize
;   letters and empty spaces that have yet to be scored;
;   updated to make its background (blank) a local helper
;
; - lsp->image 
;   Uses boxed-letter to provide a simple way to visualize
;   scored letters; updated to (a) make its background a
;   set of local functions and (b) utilize pair data
;


; boxed-letter : 1String NonNegReal [NonNegReal -> Image] -> Image
; produces an image of a letter on a background of a particular size

(check-expect
 (boxed-letter "A" 10 (λ (_) empty-image))
 (text "A" 5 BG-COLOR))

(check-expect
 (boxed-letter "B" 64 (λ (s) (overlay (square s "outline" BORDER-COLOR)
                                      (square s "solid" GUESS-COLOR))))
 (overlay
  (text "B" 32 BG-COLOR)
  (square 64 "outline" BORDER-COLOR)
  (square 64 "solid" GUESS-COLOR)))

(define (boxed-letter s size bgf)
  (overlay
   (text s (/ size 2) BG-COLOR)
   (bgf size)))


; guess-letter->image : 1String -> Image
; visualizes a guessed character

(check-expect
 (guess-letter->image "A")
 (overlay
  (text "A" (/ LT-SIZE 2) BG-COLOR)
  (square LT-SIZE "outline" BORDER-COLOR)
  (square LT-SIZE "solid" GUESS-COLOR)))

(check-expect
 (guess-letter->image "B")
 (overlay
  (text "B" (/ LT-SIZE 2) BG-COLOR)
  (square LT-SIZE "outline" BORDER-COLOR)
  (square LT-SIZE "solid" GUESS-COLOR)))

(define (guess-letter->image s)
  (local [; blank : NonNegReal -> Image
          ; produces a blank box of the appropriate size
          (define (blank size)
            (overlay (square size "outline" BORDER-COLOR)
                     (square size "solid" GUESS-COLOR)))]
    (boxed-letter s LT-SIZE blank)))


; lsp->image : LetterStatusPair -> Image
; produces a visualization of a letter with status

(check-expect (lsp->image (make-pair "A" LS-RIGHT))
              (overlay
               (text "A" (/ LT-SIZE 2) BG-COLOR)
               (square LT-SIZE "solid" "darkgreen")))

(check-expect (lsp->image (make-pair "B" LS-WRONG))
              (overlay
               (text "B" (/ LT-SIZE 2) BG-COLOR)
               (square LT-SIZE "solid" "dimgray")))

(check-expect (lsp->image (make-pair "C" LS-MISPLACED))
              (overlay
               (text "C" (/ LT-SIZE 2) BG-COLOR)
               (square LT-SIZE "solid" "goldenrod")))

(define (lsp->image lsp) 
  (local [; ls->color : LS -> Color
          ; produces a background color associated with a letter status
          (define (ls->color ls)
            (cond
              [(string=? ls LS-WRONG) "dimgray"]
              [(string=? ls LS-MISPLACED) "goldenrod"]
              [(string=? ls LS-RIGHT) "darkgreen"]))

          ; ls->color-fn : LetterStatus -> [NonNegReal -> Image]
          ; produces a function for a status-specific background
          ; of the appropriate size
          (define (ls->color-fn ls)
            (λ (size) (square size "solid" (ls->color ls))))]
    (boxed-letter
     (pair-first lsp)
     LT-SIZE
     (ls->color-fn (pair-second lsp)))))


;
; Functions (Visualizing Letters)
; -------------------------------
;
; - stack 
;   Abstraction for separating a set of images using a
;   pairwise orientation function
;
; - stack/v 
;   Uses stack to easily visualize a list of images in
;   a horizontal fashion
; 
; - stack/h 
;   Uses stack to easily visualize a list of images in
;   a horizontal fashion
;
; - row->image 
;   Uses stack/h to easily visualize a list of objects, each
;   with an associated visualization function (e.g., a list
;   of letters with one of the functions from the preceding
;   section).
;

; stack : [List-of Images] [Image Image -> Image] -> Image
; space separates a list of images via a pairwise orientation function

(check-expect
 (stack '() beside)
 GAP)

(check-expect
 (stack '() above)
 GAP)

(check-expect
 (stack
  (list
   (text "A" 5 "black")
   (text "B" 10 "black")
   (text "C" 50 "black"))
  above)
 (above
  GAP
  (text "A" 5 "black")
  GAP
  (text "B" 10 "black")
  GAP
  (text "C" 50 "black")
  GAP))

(check-expect
 (stack
  (list
   (text "A" 5 "black")
   (text "B" 10 "black")
   (text "C" 50 "black"))
  beside)
 (beside
  GAP
  (text "A" 5 "black")
  GAP
  (text "B" 10 "black")
  GAP
  (text "C" 50 "black")
  GAP))

(define (stack images addf)
  (local [; buffer : Image Image -> Image
          ; positions the supplied image
          ; relative to the supplied background
          ; with a gap
          (define (buffer i bg)
            (addf GAP i bg))]
    (foldr
     buffer
     GAP
     images)))


; stack/v : [List-of Images] -> Image
; space-separates a list of images vertically

(check-expect
 (stack/v '())
 GAP)

(check-expect
 (stack/v
  (list
   (text "A" 5 "black")
   (text "B" 10 "black")
   (text "C" 50 "black")))
 (above
  GAP
  (text "A" 5 "black")
  GAP
  (text "B" 10 "black")
  GAP
  (text "C" 50 "black")
  GAP))

(define (stack/v images)
  (stack images above))


; stack/h : [List-of Image] -> Image
; space-separates a list of images horizontally

(check-expect
 (stack/h '())
 GAP)

(check-expect
 (stack/h
  (list
   (text "A" 5 "black")
   (text "B" 10 "black")
   (text "C" 50 "black")))
 (beside
  GAP
  (text "A" 5 "black")
  GAP
  (text "B" 10 "black")
  GAP
  (text "C" 50 "black")
  GAP))

(define (stack/h images)
  (stack images beside))


; row->image : [List-of [VizPair Any]] -> Image
; horizontally visualizes a list of elements, each with an
; associated visualization function

(check-expect
 (row->image '())
 GAP)

(check-expect
 (row->image
  (list
   (make-pair "X" guess-letter->image)
   (make-pair "Y" guess-letter->image)
   (make-pair " " guess-letter->image)))
 (beside
  GAP
  (guess-letter->image "X")
  GAP
  (guess-letter->image "Y")
  GAP
  (guess-letter->image " ")
  GAP))

(check-expect
 (row->image
  (list
   (make-pair (make-pair "A" LS-RIGHT) lsp->image)
   (make-pair (make-pair "B" LS-WRONG) lsp->image)
   (make-pair (make-pair "C" LS-MISPLACED) lsp->image)))
 (beside
  GAP
  (lsp->image (make-pair "A" LS-RIGHT))
  GAP
  (lsp->image (make-pair "B" LS-WRONG))
  GAP
  (lsp->image (make-pair "C" LS-MISPLACED))
  GAP))
 
(check-expect
 (row->image
  (list
   (make-pair "X" guess-letter->image)
   (make-pair (make-pair "A" LS-RIGHT) lsp->image)))
 (beside
  GAP
  (guess-letter->image "X")
  GAP
  (lsp->image (make-pair "A" LS-RIGHT))
  GAP))

(define (row->image ips)
  (stack/h
   (map
    (λ (ip) ((pair-second ip) (pair-first ip)))
    ips)))



; value-in-list? : (X) X [List-of X] [X X -> Boolean] -> Boolean
; is the supplied value in the list according to the supplied predicate?


(check-expect (value-in-list? 1 (list) =) #f)
(check-expect (value-in-list? "a" (list) string=?) #f)
(check-expect (value-in-list? 1 (list 1 2 3) =) #t) 
(check-expect (value-in-list? 5 (list 1 2 3) =) #f) 
(check-expect (value-in-list? "a" (list "a" "b" "c") string=?) #t)
(check-expect (value-in-list? "A" (list "a" "b" "c") string-ci=?) #t)

(define (old-value-in-list? x lox p?)
  (cond
    [(empty? lox) #f]
    [(cons? lox)
     (or (p? x (first lox))
         (value-in-list? x (rest lox) p?))]))


 

; string-in-list? : String [List-of String] -> Boolean
; is the supplied string in the list? 

(check-expect (string-in-list? "a" (list)) #f)
(check-expect (string-in-list? "a" (list "a" "b" "c")) #t)
(check-expect (string-in-list? "A" (list "a" "b" "c")) #f) 
(check-expect (string-in-list? "b" (list "a" "b" "c")) #t)
(check-expect (string-in-list? "c" (list "a" "b" "c")) #t)
(check-expect (string-in-list? "d" (list "a" "b" "c")) #f) 
 
(define (string-in-list? s los)
  (value-in-list? s los string=?))

(define (value-in-list? x lox p?) 
  (ormap (λ (e) (p? x e)) lox))
   

; read-dictionary : String -> [List-of String]
; reads the words in a supplied file and capitalizes them all

(define LITTLE-WORDS 
  (list "ACT"
        "BAD"
        "CAT"
        "DAB"
        "ETA"))


(check-expect
 (read-dictionary "BAD.EVIL")
 '())

(check-expect
 (read-dictionary "little.txt")
 LITTLE-WORDS) 
  

(define (read-dictionary s) 
  (if (file-exists? s) (map string-upcase (read-lines s)) '())) 

; score : String String -> [List-of LetterStatusPair]
; Given a guess and the correct string (assumed to be the same length, and both
; uppercase), produce the resulting pairing of each character and its status

(define (score l1 l2) 
  (local [; checker Letter Letter -> LetterStatusPair
          ; checks if the letter is wrong, misplaced or right
          (define (checker x y)
            (cond
              [(string=? x y) (make-pair x LS-RIGHT)]
              [(string-in-list? x (explode l2)) (make-pair x LS-MISPLACED)] 
              [else (make-pair x LS-WRONG)]))] 
    (mymap2 (explode l1) (explode l2) checker)))
           
             
(check-expect (score "ABC" "ABC")
              (list (make-pair "A" LS-RIGHT)
                    (make-pair "B" LS-RIGHT)
                    (make-pair "C" LS-RIGHT)))

(check-expect (score "ABC" "XYZ")
              (list (make-pair "A" LS-WRONG)
                    (make-pair "B" LS-WRONG)
                    (make-pair "C" LS-WRONG)))

(check-expect (score "CBA" "ABC")
              (list (make-pair "C" LS-MISPLACED)
                    (make-pair "B" LS-RIGHT)
                    (make-pair "A" LS-MISPLACED)))

;

(check-expect (score "AAA" "ABC")
              (list (make-pair "A" LS-RIGHT)
                    (make-pair "A" LS-MISPLACED)
                    (make-pair "A" LS-MISPLACED)))

(check-expect (score "AAAXB" "ABCAD")
              (list (make-pair "A" LS-RIGHT)
                    (make-pair "A" LS-MISPLACED)
                    (make-pair "A" LS-MISPLACED)
                    (make-pair "X" LS-WRONG)
                    (make-pair "B" LS-MISPLACED)))

(check-expect (score "WEARY" "DONOR")
              (list (make-pair "W" LS-WRONG)
                    (make-pair "E" LS-WRONG)
                    (make-pair "A" LS-WRONG)
                    (make-pair "R" LS-MISPLACED)
                    (make-pair "Y" LS-WRONG)))

(check-expect (score "BROIL" "DONOR")
              (list (make-pair "B" LS-WRONG)
                    (make-pair "R" LS-MISPLACED)
                    (make-pair "O" LS-MISPLACED)
                    (make-pair "I" LS-WRONG)
                    (make-pair "L" LS-WRONG)))

(check-expect (score "ROUND" "DONOR")
              (list (make-pair "R" LS-MISPLACED)
                    (make-pair "O" LS-RIGHT) 
                    (make-pair "U" LS-WRONG)
                    (make-pair "N" LS-MISPLACED)
                    (make-pair "D" LS-MISPLACED)))

(check-expect (score "DONOR" "DONOR")
              (list (make-pair "D" LS-RIGHT)
                    (make-pair "O" LS-RIGHT)
                    (make-pair "N" LS-RIGHT)
                    (make-pair "O" LS-RIGHT)
                    (make-pair "R" LS-RIGHT)))

(check-expect (score "GOALS" "ATONE")
              (list (make-pair "G" LS-WRONG)
                    (make-pair "O" LS-MISPLACED)
                    (make-pair "A" LS-MISPLACED)
                    (make-pair "L" LS-WRONG)
                    (make-pair "S" LS-WRONG)))

(check-expect (score "AROMA" "ATONE")
              (list (make-pair "A" LS-RIGHT)
                    (make-pair "R" LS-WRONG)
                    (make-pair "O" LS-RIGHT)
                    (make-pair "M" LS-WRONG)
                    (make-pair "A" LS-MISPLACED)))

(check-expect (score "AWOKE" "ATONE")
              (list (make-pair "A" LS-RIGHT)
                    (make-pair "W" LS-WRONG)
                    (make-pair "O" LS-RIGHT)
                    (make-pair "K" LS-WRONG)
                    (make-pair "E" LS-RIGHT)))

(check-expect (score "ABODE" "ATONE")
              (list (make-pair "A" LS-RIGHT)
                    (make-pair "B" LS-WRONG)
                    (make-pair "O" LS-RIGHT)
                    (make-pair "D" LS-WRONG)
                    (make-pair "E" LS-RIGHT)))

(check-expect (score "ATONE" "ATONE")
              (list (make-pair "A" LS-RIGHT)
                    (make-pair "T" LS-RIGHT)
                    (make-pair "O" LS-RIGHT)
                    (make-pair "N" LS-RIGHT)
                    (make-pair "E" LS-RIGHT)))





; When a player is actively guessing, they may have fewer letters than are
; required of a full Wordle guess (5, in the NYT version).


; partial-guess->full-guess : String Nat -> String
; expands a guess with spaces to fill up the supplied length


(check-expect (partial-guess->full-guess "" 3) "   ")
(check-expect (partial-guess->full-guess "ABC" 3) "ABC")
(check-expect (partial-guess->full-guess "XYZ" 5) "XYZ  ")


; (example using string-based functions, see instructions above)
(define (partial-guess->full-guess/string s len)
  (string-append 
   s
   (replicate (- len (string-length s)) " ")))

(define (partial-guess->full-guess s n)
  (local [
          ; spaces-in-list : Nat -> [List-of Strings]
          ; adds all the spaces needed to a list
          (define (spaces-in-list n)
            (build-list (- n (string-length s)) (λ (x) (identity " "))))
          ; add-to-list string -> string
          ; apends the list with the given string
          (define (add-to-list s)
            (append (explode s) (spaces-in-list n)))]
    (implode (add-to-list s))))
 
                      
; partial-guess->image : String Nat -> Image
; visualizes a partial guess (up to a supplied length,
; assumed to be at least as long as the supplied string)


(check-expect
 (partial-guess->image "" 3)
 (beside GAP
         (guess-letter->image " ")
         GAP
         (guess-letter->image " ")
         GAP
         (guess-letter->image " ")
         GAP))

(check-expect
 (partial-guess->image "ABC" 3)
 (beside GAP
         (guess-letter->image "A")
         GAP
         (guess-letter->image "B")
         GAP
         (guess-letter->image "C")
         GAP))

(check-expect
 (partial-guess->image "XYZ" 5)
 (beside GAP
         (guess-letter->image "X")
         GAP
         (guess-letter->image "Y")
         GAP
         (guess-letter->image "Z")
         GAP
         (guess-letter->image " ")
         GAP
         (guess-letter->image " ")
         GAP))

(define (partial-guess->image s len)
  (row->image
   (map
    (λ (l) (make-pair l guess-letter->image))
    (explode
     (partial-guess->full-guess s len)))))


(define-struct wordle [past current])

; A WordleState (WS) is a (make-wordle [List-of String] String)
; Interpretation: the previous and current guess in a Wordle game

(define WS-START (make-wordle '() ""))
(define WS-TYPE1 (make-wordle '() "C"))
(define WS-TYPE2 (make-wordle '() "CA"))
(define WS-TYPE3 (make-wordle '() "CAT"))
(define WS-TYPE-BAD (make-wordle '() "CAX")) 
(define WS-WORD1 (make-wordle (list "CAT") ""))
(define WS-WORD2 (make-wordle (list "CAT" "DAB") ""))
(define WS-WORD3 (make-wordle (list "CAT" "DAB" "BAD") ""))


; key-wordle : WS KeyEvent [List-of String] Nat -> WS
; processes a keyboard event given a list of valid words and the required word length


(define (key-wordle ws k los n)
  (local [; valid-letter? : 1Stirng -> Boolean
          ; checks if the 1string is a letter
          (define (valid-letter? 1s)
            (string-in-list? (string-downcase 1s) (explode "abcdefghijklmnopqrstuvwxyz")))]
    (cond
      [(key=? "\b" k) (backspace-helper ws)]
      [(key=? "\r" k) (return-helper k n ws los)]
      [(valid-letter? k) (letter-helper k n ws)]                
      [else ws])))

; return-helper : Key Number WS [List-of String] -> WS
; output a new WS based on the key number list of strings and current WS

(check-expect (return-helper "\r" 3 WS-START LITTLE-WORDS) WS-START) 
(check-expect (return-helper "\r" 3 WS-TYPE1 LITTLE-WORDS) WS-TYPE1)
(check-expect (return-helper "\r" 3 WS-TYPE2 LITTLE-WORDS) WS-TYPE2)

(define (return-helper k n ws los)
  (if (and (= (string-length (wordle-current ws)) n) (string-in-list? (wordle-current ws) los))
      (make-wordle (append (wordle-past ws) (list (wordle-current ws)))  "")
      ws))
          


; letter-helper : Key Number WS -> WS
; output a new WS based on the key number and current WS

(check-expect (letter-helper "C" 3 WS-START) WS-TYPE1)
(check-expect (letter-helper "c" 3 WS-START) WS-TYPE1)


(define (letter-helper k n ws)
  (if (< (string-length (wordle-current ws)) n)
      (make-wordle (wordle-past ws) (string-append (wordle-current ws) (string-upcase k)))
      ws))
      


; backspace-helper : WS -> WS   
; updates word space when "\b" is clicked

(check-expect (backspace-helper WS-TYPE3) WS-TYPE2)
(check-expect (backspace-helper WS-TYPE2) WS-TYPE1)
(check-expect (backspace-helper WS-TYPE1) WS-START)
 
(define (backspace-helper ws)
  (if (= 0 (string-length (wordle-current ws))) 
      ws 
      (make-wordle (wordle-past ws) (substring (wordle-current ws)
                                               0
                                               (- (string-length (wordle-current ws)) 1)))))
      
    
 

; backspace tests
(check-expect
 (key-wordle WS-START "\b" LITTLE-WORDS 3)
 WS-START)

(check-expect
 (key-wordle WS-TYPE1 "\b" LITTLE-WORDS 3)
 WS-START)

(check-expect
 (key-wordle WS-TYPE2 "\b" LITTLE-WORDS 3)
 WS-TYPE1)

(check-expect
 (key-wordle WS-TYPE3 "\b" LITTLE-WORDS 3)
 WS-TYPE2)

(check-expect
 (key-wordle WS-TYPE-BAD "\b" LITTLE-WORDS 3)
 WS-TYPE2)
 
(check-expect
 (key-wordle WS-WORD1 "\b" LITTLE-WORDS 3)
 WS-WORD1) 

; return tests
(check-expect
 (key-wordle WS-START "\r" LITTLE-WORDS 3)
 WS-START)

(check-expect
 (key-wordle WS-TYPE1 "\r" LITTLE-WORDS 3)
 WS-TYPE1)

(check-expect
 (key-wordle WS-TYPE2 "\r" LITTLE-WORDS 3)
 WS-TYPE2)

(check-expect
 (key-wordle WS-TYPE3 "\r" LITTLE-WORDS 3)
 WS-WORD1)

(check-expect 
 (key-wordle WS-TYPE-BAD "\r" LITTLE-WORDS 3)
 WS-TYPE-BAD) 
 
(check-expect
 (key-wordle WS-WORD1 "\r" LITTLE-WORDS 3)
 WS-WORD1)

; other keys
(check-expect
 (key-wordle WS-START "C" LITTLE-WORDS 3)
 WS-TYPE1)

(check-expect
 (key-wordle WS-START "c" LITTLE-WORDS 3)
 WS-TYPE1)

(check-expect
 (key-wordle WS-START "1" LITTLE-WORDS 3)
 WS-START)

(check-expect
 (key-wordle WS-TYPE1 "A" LITTLE-WORDS 3)
 WS-TYPE2)

(check-expect
 (key-wordle WS-TYPE1 "A" LITTLE-WORDS 1) 
 WS-TYPE1) 

(check-expect
 (key-wordle WS-TYPE1 "2" LITTLE-WORDS 3)
 WS-TYPE1)

(check-expect
 (key-wordle WS-TYPE2 "T" LITTLE-WORDS 3)
 WS-TYPE3)

(check-expect
 (key-wordle WS-TYPE2 "X" LITTLE-WORDS 3)
 WS-TYPE-BAD)

(check-expect
 (key-wordle WS-TYPE3 "S" LITTLE-WORDS 3)
 WS-TYPE3)


; end-wordle? : WS String Nat -> Boolean
; determines if the game is over, either because the
; (supplied) maximum number of guesses has been made,
; or the (supplied) correct word had been previously
; guessed
 
(define (end-wordle? ws s n)
  (or (string-in-list? s (wordle-past ws))
      (>= (length (wordle-past ws)) n))) 
 

(check-expect (end-wordle? WS-START "BAD" 5) #false)
(check-expect (end-wordle? WS-TYPE1 "BAD" 5) #false)
(check-expect (end-wordle? WS-TYPE2 "BAD" 5) #false)
(check-expect (end-wordle? WS-TYPE3 "BAD" 5) #false)
(check-expect (end-wordle? WS-TYPE-BAD "BAD" 5) #false)
(check-expect (end-wordle? WS-WORD1 "BAD" 5) #false)
(check-expect (end-wordle? WS-WORD1 "BAD" 1) #true)
(check-expect (end-wordle? WS-WORD1 "CAT" 3) #true)

(check-expect (end-wordle? WS-WORD2 "BAD" 5) #false)
(check-expect (end-wordle? WS-WORD2 "BAD" 2) #true)
(check-expect (end-wordle? WS-WORD2 "DAB" 2) #true)

(check-expect (end-wordle? WS-WORD3 "BAD" 5) #true)
(check-expect (end-wordle? WS-WORD3 "ETA" 5) #false)



; draw-end-wordle : WS String Nat -> Image
; produces the game end screen

(check-expect (draw-end-wordle WS-WORD2 "BAD" 5) (overlay
                                                  (text "you lose" (* 5 5) "white")
                                                  (rectangle (* 5 75) (* 5 75) "solid" "red")))  
                                    

(check-expect (draw-end-wordle WS-WORD2 "DAB" 2) (overlay
                                                  (text
                                                   (string-append "you win! It took you 2 guesses")
                                                   (* 2 3)
                                                   "white") 
                                                  (rectangle (* 2 75) (* 2 75) "solid" "green")))

 
(define (draw-end-wordle ws s n)
  (if (ormap (λ (x) (string=? s x)) (wordle-past ws))
      (overlay (text
                (string-append "you win! It took you " (number->string
                                                        (length (wordle-past ws))) " guesses")
                (* n 3)
                "white") 
               (rectangle (* n 75) (* n 75) "solid" "green"))
      (overlay (text "you lose" (* n 5) "white") 
               (rectangle (* n 75) (* n 75) "solid" "red"))))   
                                     

; draw-wordle : WS String Nat -> Image
; visualizes the play state of wordle given the correct answer
; and number of available guesses

      
 
(define (draw-wordle ws s n)
  (local [; background : Number String -> Image
          ; outputs the wordle background based on the number of guesses and the correct word
          (define (background n s)
            (build-list (- n 1) (λ (x) (partial-guess->image "" (string-length s)))))
          ; score-wordle : WS -> [List-of [List-of [VizPair LetterStatus]]]
          ; socres past wordle guesses
          (define (score-wordle ws)
            (map (λ (x) (update-pair (score x s))) (wordle-past ws)))
          ; update-pair : [List-of LetterStatusPair] -> [List-of [VizPair LetterStatus]]
          ; makes a new [List-of LetterStatusPair] using the old one
          (define (update-pair lsp)
            (map (λ (x) (make-pair x lsp->image)) lsp))
          ; draw-current-word : WS -> Image
          ; draw the supplied word
          (define (draw-current-word ws)
            (partial-guess->image (wordle-current ws) (string-length s)))]
    (stack/v (append (map row->image (score-wordle ws)) (list (draw-current-word ws))
                     (background n s)))))
   


; play : String Nat String -> [List-of String]
; starts a game of Wordle given the correct answer,
; the number of guesses, and the location of the valid
; guesses and produces the guesses
   
(define (play correct num-guesses guesses-file)
  (local [(define VALID (read-dictionary guesses-file))
          (define UP-CORRECT (string-upcase correct))]
    (wordle-past
     (big-bang WS-START
       [to-draw (λ (ws) (draw-wordle ws UP-CORRECT num-guesses))]
       [on-key (λ (ws ke) (key-wordle ws ke VALID (string-length UP-CORRECT)))]
       [stop-when (λ (ws) (end-wordle? ws UP-CORRECT num-guesses))
                  (λ (ws) (draw-end-wordle ws UP-CORRECT num-guesses))]))))
 
     
; Make sure you understand the function, and then...

; run for a tiny, testing game:
; (play "bad" 4 "little.txt")

; run for full-on prior game from the NYT
; 

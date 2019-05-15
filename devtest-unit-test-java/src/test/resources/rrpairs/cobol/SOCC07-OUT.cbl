      ******************************************************************        
      * CTLM BANQUE : FICHE FONCTIONNALITE SERV0002, RECH. MANUEL      *        
      *                         - VERSION V2.8 -                       *        
      ******************************************************************        
      *                                                                         
      ******************************************************************        
      * ID-CMN-CC : 20070622-150131 SOCC071L_ECRD008780                         
      ******************************************************************        
      * PROJET : MARS - BCC FACET                                      *        
      * --------                                                       *        
      * FONCTIONNEL : PERS06IRCH-RECHERCHER UNE PERSONNE PHYSIQUE      *        
      * --------                                                       *        
      *                                                                *        
      * COMMAREA D'ECHANGE CTG POUR LA METHODE D'ACCES :               *        
      *      1)  LIST-PERSON-BY-EXTERNALVA                             *        
      *      2)  LIST-PERSON-BY-ADDRESSPH                              *        
      *      3)  LIST-PERSON-BY-ADDRESSMAILLB                          *        
      *      4)  LIST-PERSON-BY-PHONETICCD                             *        
      *      5)  LIST-PERSON-BY-PHCD-POSTCD                            *        
      *      6)  LIST-PERSON-BY-PHCD-BIRTHDT                           *        
      *      7)  LIST-PERSON-BY-PHCD-BIRTHZPCD                         *        
      *      8)  LIST-PERSON-BY-PHCD-ALL                               *        
      *      9)  LIST-PERSON-BY-PHCD-A-POSTCD                          *        
      *      10) LIST-PERSON-BY-PHCD-A-BIRTHDT                         *        
      *      11) LIST-PERSON-BY-PHCD-A-BIRTHZCD                        *        
      *      12) LIST-PERSON-BY-FOYERID                                *        
      *      13) LIST-PERS-REF-PRODUCT                                 *1008UCBQ
      *                                                                *        
      ******************************************************************        
       01 SOCC071L-COMMAREA.                                                    
      *   TECHNICAL CONTEXT AREA                                                
          05 SOCC071L-CONTEXT-AREA       PIC X(130).                            
                                                                                
      *   APPLICATION AREA                                                      
      *UC 05 SOCC071L-DATA-AREA          PIC X(11000).                          
      *   05 SOCC071L-DATA-AREA          PIC X(21000).                  1008UCBQ
                                                                                
      *--- ACCESS METHOD --------------------------------------                 
      *   *------------------------------*                                      
      *   *---- PARAMETRES EN SORTIE ----*                                      
      *   *------------------------------*                                      
      *   05 SOCC071L-DATA-AREA-OUT REDEFINES SOCC071L-DATA-AREA.               
          05 SOCC071L-DATA-AREA-OUT.               
      *   *** NOMBRE DE LIGNES RESTITUEES                                       
             10  SOCC071L-NB-LINE               PIC 9(3).                       
      *   *** INDICATEUR DE PAGE SUIVANTE (Y/N)                                 
             10  SOCC071L-PAGE-SUIVANTE         PIC X(1).                       
      *   *** TABLEAU DES PERSONNES                                             
             10  SOCC071L-PERSON-ARRAY.                                         
                 15 SOCC071L-PERSON-OCC            OCCURS 50 TIMES.             
      *   *** IDENTIFIANT DE LA PERSONNE                                        
                    20 SOCC071L-PERSONENTITYID     PIC 9(11).                   
      *   *** NOM D'USAGE DE LA PERSONNE                                        
                    20 SOCC071L-PERSONUSAGENAMELB  PIC X(50).                   
      *   *** PRENOM DE NAISSANCE                                               
                    20 SOCC071L-BIRTHFORENAMELB    PIC X(30).                   
      *   *** DATE DE NAISSANCE DE LA PERSONNE                                  
                    20 SOCC071L-PERSONBIRTHDT      PIC 9(8).                    
      *   *** DEPARTEMENT DE NAISSANCE DE LA PERSONNE                           
                    20 SOCC071L-PERSONBIRTHZIPCD   PIC X(10).                   
      *   *** CODE POSTAL DE L'ADRESSE                                          
                    20 SOCC071L-ADDRESSPOSTALCD    PIC X(10).                   
      *   *** VILLE DE L'ADRESSE                                                
                    20 SOCC071L-ADDRESSCITYLB      PIC X(27).                   
      *   *** LIGNE 4 DE L'ADRESSE                                              
                    20 SOCC071L-ADDRESS4STREETLB   PIC X(38).                   
      *   *** IDENTIFIANT FOYER                                                 
                    20 SOCC071L-FOYERID            PIC 9(11).                   
      *   *** RANG DE LA PERSONNE                                               
                    20 SOCC071L-PERSONRANKID       PIC 9(1).                    
      *   *** IDENTIFIANT COMMERCIALISATEUR                                     
                    20 SOCC071L-CREDITTRADERID     PIC 9(11).                   
      *   *** REFERENCE CLIENT DISTRIBUTEUR                             1008UCBQ
                    20 SOCC071L-TRADEREXTREFID     PIC X(20).           1008UCBQ
      *   *** INDICATEUR FOYER PRINCIPAL                                1008UCBQ
                    20 SOCC071L-IND-FOYER-PRINC    PIC X(01).           1008UCBQ
      *   *** INDICATEUR FOYER PRIVILIGIE                               1008UCBQ
                    20 SOCC071L-IND-FOYER-PRIVI    PIC X(01).           1008UCBQ
      *   *** IDENTIFIANT TECHNIQUE DE L'INFO COMMERCIALE               1211PEIM
                    20 SOCC071L-TRADINGINFOID      PIC 9(11).           1211PEIM
1406IM*   *** IDENTIFIANT ESPACE CLIENT                                 1211PEIM
1406IM*             20 SOCC071L-TWFOCID            PIC X(10).           1211PEIM
                    20 FILLER                      PIC X(10).           1406IMGY
      *   *** FILLER                                                            
      *UC    10  FILLER                         PIC X(646).                     
      *UC    10  FILLER                         PIC X(9596).            1008UCBQ
      *PE    10  FILLER                         PIC X(9546).            1008UCBQ
             10  FILLER                         PIC X(8496).            1211PEIM
      *
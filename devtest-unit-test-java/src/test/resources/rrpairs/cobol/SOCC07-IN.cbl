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
      *   *---- PARAMETRES EN ENTREE ----*                                      
      *   *------------------------------*                                      
      *    05 SOCC071L-DATA-AREA-IN REDEFINES SOCC071L-DATA-AREA.                
           05 SOCC071L-DATA-AREA-IN.                 
      *   *** IDENTIFIANT EXTERNE                                               
             10 SOCC071L-EXTERNALVA             PIC X(20).                      
      *   *** TELEPHONE DE LA PERSONNE                                          
             10 SOCC071L-ADDRESSPH              PIC X(15).                      
      *   *** EMAIL DE LA PERSONNE                                              
             10 SOCC071L-ADDRESSMAILLB          PIC X(80).                      
      *   *** CODE PHONETIQUE DE LA PERSONNE                                    
             10 SOCC071L-PERSONPHONETICCD       PIC X(10).                      
      *   *** CODE POSTAL DE L'ADRESSE                                          
             10 SOCC071L-ADDRESSPOSTALCD        PIC X(10).                      
      *   *** DATE DE NAISSANCE DE LA PERSONNE - DEBUT                          
             10 SOCC071L-PERSONBIRTHDT-DEB      PIC 9(8).                       
      *   *** DATE DE NAISSANCE DE LA PERSONNE - FIN                            
             10 SOCC071L-PERSONBIRTHDT-FIN      PIC 9(8).                       
      *   *** DEPARTEMENT DE NAISSANCE DE LA PERSONNE                           
             10 SOCC071L-PERSONBIRTHZIPCD       PIC X(10).                      
      *   *** IDENTIFIANT DE LA PERSONNE                                        
             10 SOCC071L-PERSONENTITYID         PIC 9(11).                      
      *   *** NUMERO DE FOYER                                                   
             10 SOCC071L-FOYERID                PIC 9(11).                      
      *   *** IDENTIFIANT COMMERCIALISATEUR                                     
             10 SOCC071L-CREDITTRADERID         PIC 9(11).                      
      *   *** NOMBRE DE LIGNES MAXIMUM A RESTITUER                              
             10 SOCC071L-NBLINE-MAX             PIC 9(3).                       
      *   *** REFERENCE CONTRAT PRODUCTEUR                              1008UCBQ
             10 SOCC071L-PRODUCERREFCONTCD      PIC X(27).              1008UCBQ
1406IM*   *** IDENTIFIANT ESPACE CLIENT                                 1211PEIM
1406IM*      10 SOCC071L-TWFOCID                PIC X(10).              1211PEIM
      *   *** FILLER                                                            
      *UC    10 FILLER                          PIC X(10803).                   
      *PE    10 FILLER                          PIC X(20776).           1008UCBQ
1406IM*      10 FILLER                          PIC X(20766).           1211PEIM
             10 FILLER                          PIC X(20776).           1406IMSK
      *

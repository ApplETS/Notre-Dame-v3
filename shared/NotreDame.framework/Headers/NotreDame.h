#import <Foundation/Foundation.h>

@class NotreDameActivite, NotreDameCours, NotreDameEnseignant, NotreDameEtudiant, NotreDameEvaluation, NotreDameEvaluationCours, NotreDameHoraireExamenFinal, NotreDameJourRemplace, NotreDameProgramme, NotreDameSeance, NotreDameSession, NotreDameSignetsUserCredentials, NotreDameUniversalCode, NotreDameSignetsUserCredentialsCompanion, NotreDameSommaireElementsEvaluation, NotreDameSommaireEtEvaluations, NotreDameUniversalCodeError, NotreDameKotlinEnum, NotreDameKotlinNumber;

@protocol NotreDameAndroidParcel, NotreDameKotlinComparable;

NS_ASSUME_NONNULL_BEGIN

@interface KotlinBase : NSObject
- (instancetype)init __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
+ (void)initialize __attribute__((objc_requires_super));
@end;

@interface KotlinBase (KotlinBaseCopying) <NSCopying>
@end;

__attribute__((objc_runtime_name("KotlinMutableSet")))
__attribute__((swift_name("KotlinMutableSet")))
@interface NotreDameMutableSet<ObjectType> : NSMutableSet<ObjectType>
@end;

__attribute__((objc_runtime_name("KotlinMutableDictionary")))
__attribute__((swift_name("KotlinMutableDictionary")))
@interface NotreDameMutableDictionary<KeyType, ObjectType> : NSMutableDictionary<KeyType, ObjectType>
@end;

@interface NSError (NSErrorKotlinException)
@property (readonly) id _Nullable kotlinException;
@end;

__attribute__((objc_runtime_name("KotlinNumber")))
__attribute__((swift_name("KotlinNumber")))
@interface NotreDameNumber : NSNumber
- (instancetype)initWithChar:(char)value __attribute__((unavailable));
- (instancetype)initWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
- (instancetype)initWithShort:(short)value __attribute__((unavailable));
- (instancetype)initWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
- (instancetype)initWithInt:(int)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
- (instancetype)initWithLong:(long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
- (instancetype)initWithLongLong:(long long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
- (instancetype)initWithFloat:(float)value __attribute__((unavailable));
- (instancetype)initWithDouble:(double)value __attribute__((unavailable));
- (instancetype)initWithBool:(BOOL)value __attribute__((unavailable));
- (instancetype)initWithInteger:(NSInteger)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
+ (instancetype)numberWithChar:(char)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
+ (instancetype)numberWithShort:(short)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
+ (instancetype)numberWithInt:(int)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
+ (instancetype)numberWithLong:(long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
+ (instancetype)numberWithLongLong:(long long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
+ (instancetype)numberWithFloat:(float)value __attribute__((unavailable));
+ (instancetype)numberWithDouble:(double)value __attribute__((unavailable));
+ (instancetype)numberWithBool:(BOOL)value __attribute__((unavailable));
+ (instancetype)numberWithInteger:(NSInteger)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
@end;

__attribute__((objc_runtime_name("KotlinByte")))
__attribute__((swift_name("KotlinByte")))
@interface NotreDameByte : NotreDameNumber
- (instancetype)initWithChar:(char)value;
+ (instancetype)numberWithChar:(char)value;
@end;

__attribute__((objc_runtime_name("KotlinUByte")))
__attribute__((swift_name("KotlinUByte")))
@interface NotreDameUByte : NotreDameNumber
- (instancetype)initWithUnsignedChar:(unsigned char)value;
+ (instancetype)numberWithUnsignedChar:(unsigned char)value;
@end;

__attribute__((objc_runtime_name("KotlinShort")))
__attribute__((swift_name("KotlinShort")))
@interface NotreDameShort : NotreDameNumber
- (instancetype)initWithShort:(short)value;
+ (instancetype)numberWithShort:(short)value;
@end;

__attribute__((objc_runtime_name("KotlinUShort")))
__attribute__((swift_name("KotlinUShort")))
@interface NotreDameUShort : NotreDameNumber
- (instancetype)initWithUnsignedShort:(unsigned short)value;
+ (instancetype)numberWithUnsignedShort:(unsigned short)value;
@end;

__attribute__((objc_runtime_name("KotlinInt")))
__attribute__((swift_name("KotlinInt")))
@interface NotreDameInt : NotreDameNumber
- (instancetype)initWithInt:(int)value;
+ (instancetype)numberWithInt:(int)value;
@end;

__attribute__((objc_runtime_name("KotlinUInt")))
__attribute__((swift_name("KotlinUInt")))
@interface NotreDameUInt : NotreDameNumber
- (instancetype)initWithUnsignedInt:(unsigned int)value;
+ (instancetype)numberWithUnsignedInt:(unsigned int)value;
@end;

__attribute__((objc_runtime_name("KotlinLong")))
__attribute__((swift_name("KotlinLong")))
@interface NotreDameLong : NotreDameNumber
- (instancetype)initWithLongLong:(long long)value;
+ (instancetype)numberWithLongLong:(long long)value;
@end;

__attribute__((objc_runtime_name("KotlinULong")))
__attribute__((swift_name("KotlinULong")))
@interface NotreDameULong : NotreDameNumber
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value;
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value;
@end;

__attribute__((objc_runtime_name("KotlinFloat")))
__attribute__((swift_name("KotlinFloat")))
@interface NotreDameFloat : NotreDameNumber
- (instancetype)initWithFloat:(float)value;
+ (instancetype)numberWithFloat:(float)value;
@end;

__attribute__((objc_runtime_name("KotlinDouble")))
__attribute__((swift_name("KotlinDouble")))
@interface NotreDameDouble : NotreDameNumber
- (instancetype)initWithDouble:(double)value;
+ (instancetype)numberWithDouble:(double)value;
@end;

__attribute__((objc_runtime_name("KotlinBoolean")))
__attribute__((swift_name("KotlinBoolean")))
@interface NotreDameBoolean : NotreDameNumber
- (instancetype)initWithBool:(BOOL)value;
+ (instancetype)numberWithBool:(BOOL)value;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Activite")))
@interface NotreDameActivite : KotlinBase
- (instancetype)initWithSigle:(NSString *)sigle groupe:(NSString * _Nullable)groupe jour:(int32_t)jour journee:(NSString * _Nullable)journee codeActivite:(NSString * _Nullable)codeActivite nomActivite:(NSString * _Nullable)nomActivite activitePrincipale:(NSString * _Nullable)activitePrincipale heureDebut:(NSString * _Nullable)heureDebut heureFin:(NSString * _Nullable)heureFin local:(NSString * _Nullable)local titreCours:(NSString * _Nullable)titreCours __attribute__((swift_name("init(sigle:groupe:jour:journee:codeActivite:nomActivite:activitePrincipale:heureDebut:heureFin:local:titreCours:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (NSString * _Nullable)component2 __attribute__((swift_name("component2()")));
- (int32_t)component3 __attribute__((swift_name("component3()")));
- (NSString * _Nullable)component4 __attribute__((swift_name("component4()")));
- (NSString * _Nullable)component5 __attribute__((swift_name("component5()")));
- (NSString * _Nullable)component6 __attribute__((swift_name("component6()")));
- (NSString * _Nullable)component7 __attribute__((swift_name("component7()")));
- (NSString * _Nullable)component8 __attribute__((swift_name("component8()")));
- (NSString * _Nullable)component9 __attribute__((swift_name("component9()")));
- (NSString * _Nullable)component10 __attribute__((swift_name("component10()")));
- (NSString * _Nullable)component11 __attribute__((swift_name("component11()")));
- (NotreDameActivite *)doCopySigle:(NSString *)sigle groupe:(NSString * _Nullable)groupe jour:(int32_t)jour journee:(NSString * _Nullable)journee codeActivite:(NSString * _Nullable)codeActivite nomActivite:(NSString * _Nullable)nomActivite activitePrincipale:(NSString * _Nullable)activitePrincipale heureDebut:(NSString * _Nullable)heureDebut heureFin:(NSString * _Nullable)heureFin local:(NSString * _Nullable)local titreCours:(NSString * _Nullable)titreCours __attribute__((swift_name("doCopy(sigle:groupe:jour:journee:codeActivite:nomActivite:activitePrincipale:heureDebut:heureFin:local:titreCours:)")));
@property NSString *sigle;
@property NSString * _Nullable groupe;
@property int32_t jour;
@property NSString * _Nullable journee;
@property NSString * _Nullable codeActivite;
@property NSString * _Nullable nomActivite;
@property NSString * _Nullable activitePrincipale;
@property NSString * _Nullable heureDebut;
@property NSString * _Nullable heureFin;
@property NSString * _Nullable local;
@property NSString * _Nullable titreCours;
@end;

__attribute__((swift_name("AndroidParcel")))
@protocol NotreDameAndroidParcel
@required
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Cours")))
@interface NotreDameCours : KotlinBase <NotreDameAndroidParcel>
- (instancetype)initWithSigle:(NSString *)sigle groupe:(NSString *)groupe session:(NSString *)session programmeEtudes:(NSString *)programmeEtudes cote:(NSString * _Nullable)cote noteSur100:(NSString * _Nullable)noteSur100 nbCredits:(int32_t)nbCredits titreCours:(NSString *)titreCours __attribute__((swift_name("init(sigle:groupe:session:programmeEtudes:cote:noteSur100:nbCredits:titreCours:)"))) __attribute__((objc_designated_initializer));
- (BOOL)hasValidSession __attribute__((swift_name("hasValidSession()")));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (NSString *)component2 __attribute__((swift_name("component2()")));
- (NSString *)component3 __attribute__((swift_name("component3()")));
- (NSString *)component4 __attribute__((swift_name("component4()")));
- (NSString * _Nullable)component5 __attribute__((swift_name("component5()")));
- (NSString * _Nullable)component6 __attribute__((swift_name("component6()")));
- (int32_t)component7 __attribute__((swift_name("component7()")));
- (NSString *)component8 __attribute__((swift_name("component8()")));
- (NotreDameCours *)doCopySigle:(NSString *)sigle groupe:(NSString *)groupe session:(NSString *)session programmeEtudes:(NSString *)programmeEtudes cote:(NSString * _Nullable)cote noteSur100:(NSString * _Nullable)noteSur100 nbCredits:(int32_t)nbCredits titreCours:(NSString *)titreCours __attribute__((swift_name("doCopy(sigle:groupe:session:programmeEtudes:cote:noteSur100:nbCredits:titreCours:)")));
@property NSString *sigle;
@property NSString *groupe;
@property NSString *session;
@property NSString *programmeEtudes;
@property NSString * _Nullable cote;
@property NSString * _Nullable noteSur100;
@property int32_t nbCredits;
@property NSString *titreCours;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Enseignant")))
@interface NotreDameEnseignant : KotlinBase
- (instancetype)initWithLocalBureau:(NSString * _Nullable)localBureau telephone:(NSString * _Nullable)telephone enseignantPrincipal:(NSString * _Nullable)enseignantPrincipal nom:(NSString * _Nullable)nom prenom:(NSString * _Nullable)prenom courriel:(NSString *)courriel __attribute__((swift_name("init(localBureau:telephone:enseignantPrincipal:nom:prenom:courriel:)"))) __attribute__((objc_designated_initializer));
- (NSString * _Nullable)component1 __attribute__((swift_name("component1()")));
- (NSString * _Nullable)component2 __attribute__((swift_name("component2()")));
- (NSString * _Nullable)component3 __attribute__((swift_name("component3()")));
- (NSString * _Nullable)component4 __attribute__((swift_name("component4()")));
- (NSString * _Nullable)component5 __attribute__((swift_name("component5()")));
- (NSString *)component6 __attribute__((swift_name("component6()")));
- (NotreDameEnseignant *)doCopyLocalBureau:(NSString * _Nullable)localBureau telephone:(NSString * _Nullable)telephone enseignantPrincipal:(NSString * _Nullable)enseignantPrincipal nom:(NSString * _Nullable)nom prenom:(NSString * _Nullable)prenom courriel:(NSString *)courriel __attribute__((swift_name("doCopy(localBureau:telephone:enseignantPrincipal:nom:prenom:courriel:)")));
@property NSString * _Nullable localBureau;
@property NSString * _Nullable telephone;
@property NSString * _Nullable enseignantPrincipal;
@property NSString * _Nullable nom;
@property NSString * _Nullable prenom;
@property NSString *courriel;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Etudiant")))
@interface NotreDameEtudiant : KotlinBase
- (instancetype)initWithType:(NSString *)type nom:(NSString *)nom prenom:(NSString *)prenom codePerm:(NSString *)codePerm soldeTotal:(NSString *)soldeTotal masculin:(BOOL)masculin __attribute__((swift_name("init(type:nom:prenom:codePerm:soldeTotal:masculin:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (NSString *)component2 __attribute__((swift_name("component2()")));
- (NSString *)component3 __attribute__((swift_name("component3()")));
- (NSString *)component4 __attribute__((swift_name("component4()")));
- (NSString *)component5 __attribute__((swift_name("component5()")));
- (BOOL)component6 __attribute__((swift_name("component6()")));
- (NotreDameEtudiant *)doCopyType:(NSString *)type nom:(NSString *)nom prenom:(NSString *)prenom codePerm:(NSString *)codePerm soldeTotal:(NSString *)soldeTotal masculin:(BOOL)masculin __attribute__((swift_name("doCopy(type:nom:prenom:codePerm:soldeTotal:masculin:)")));
@property NSString *type;
@property NSString *nom;
@property NSString *prenom;
@property NSString *codePerm;
@property NSString *soldeTotal;
@property BOOL masculin;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Evaluation")))
@interface NotreDameEvaluation : KotlinBase
- (instancetype)initWithCours:(NSString *)cours groupe:(NSString *)groupe session:(NSString *)session nom:(NSString *)nom equipe:(NSString *)equipe dateCible:(id _Nullable)dateCible note:(NSString *)note corrigeSur:(NSString *)corrigeSur notePourcentage:(NSString *)notePourcentage ponderation:(NSString *)ponderation moyenne:(NSString *)moyenne moyennePourcentage:(NSString *)moyennePourcentage ecartType:(NSString *)ecartType mediane:(NSString *)mediane rangCentile:(NSString *)rangCentile publie:(BOOL)publie messageDuProf:(NSString *)messageDuProf ignoreDuCalcul:(BOOL)ignoreDuCalcul __attribute__((swift_name("init(cours:groupe:session:nom:equipe:dateCible:note:corrigeSur:notePourcentage:ponderation:moyenne:moyennePourcentage:ecartType:mediane:rangCentile:publie:messageDuProf:ignoreDuCalcul:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (NSString *)component2 __attribute__((swift_name("component2()")));
- (NSString *)component3 __attribute__((swift_name("component3()")));
- (NSString *)component4 __attribute__((swift_name("component4()")));
- (NSString *)component5 __attribute__((swift_name("component5()")));
- (id _Nullable)component6 __attribute__((swift_name("component6()")));
- (NSString *)component7 __attribute__((swift_name("component7()")));
- (NSString *)component8 __attribute__((swift_name("component8()")));
- (NSString *)component9 __attribute__((swift_name("component9()")));
- (NSString *)component10 __attribute__((swift_name("component10()")));
- (NSString *)component11 __attribute__((swift_name("component11()")));
- (NSString *)component12 __attribute__((swift_name("component12()")));
- (NSString *)component13 __attribute__((swift_name("component13()")));
- (NSString *)component14 __attribute__((swift_name("component14()")));
- (NSString *)component15 __attribute__((swift_name("component15()")));
- (BOOL)component16 __attribute__((swift_name("component16()")));
- (NSString *)component17 __attribute__((swift_name("component17()")));
- (BOOL)component18 __attribute__((swift_name("component18()")));
- (NotreDameEvaluation *)doCopyCours:(NSString *)cours groupe:(NSString *)groupe session:(NSString *)session nom:(NSString *)nom equipe:(NSString *)equipe dateCible:(id _Nullable)dateCible note:(NSString *)note corrigeSur:(NSString *)corrigeSur notePourcentage:(NSString *)notePourcentage ponderation:(NSString *)ponderation moyenne:(NSString *)moyenne moyennePourcentage:(NSString *)moyennePourcentage ecartType:(NSString *)ecartType mediane:(NSString *)mediane rangCentile:(NSString *)rangCentile publie:(BOOL)publie messageDuProf:(NSString *)messageDuProf ignoreDuCalcul:(BOOL)ignoreDuCalcul __attribute__((swift_name("doCopy(cours:groupe:session:nom:equipe:dateCible:note:corrigeSur:notePourcentage:ponderation:moyenne:moyennePourcentage:ecartType:mediane:rangCentile:publie:messageDuProf:ignoreDuCalcul:)")));
@property NSString *cours;
@property NSString *groupe;
@property NSString *session;
@property NSString *nom;
@property NSString *equipe;
@property id _Nullable dateCible;
@property NSString *note;
@property NSString *corrigeSur;
@property NSString *notePourcentage;
@property NSString *ponderation;
@property NSString *moyenne;
@property NSString *moyennePourcentage;
@property NSString *ecartType;
@property NSString *mediane;
@property NSString *rangCentile;
@property BOOL publie;
@property NSString *messageDuProf;
@property BOOL ignoreDuCalcul;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("EvaluationCours")))
@interface NotreDameEvaluationCours : KotlinBase
- (instancetype)initWithSession:(NSString *)session dateDebutEvaluation:(double)dateDebutEvaluation dateFinEvaluation:(double)dateFinEvaluation enseignant:(NSString *)enseignant estComplete:(BOOL)estComplete groupe:(NSString *)groupe sigle:(NSString *)sigle typeEvaluation:(NSString *)typeEvaluation __attribute__((swift_name("init(session:dateDebutEvaluation:dateFinEvaluation:enseignant:estComplete:groupe:sigle:typeEvaluation:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (double)component2 __attribute__((swift_name("component2()")));
- (double)component3 __attribute__((swift_name("component3()")));
- (NSString *)component4 __attribute__((swift_name("component4()")));
- (BOOL)component5 __attribute__((swift_name("component5()")));
- (NSString *)component6 __attribute__((swift_name("component6()")));
- (NSString *)component7 __attribute__((swift_name("component7()")));
- (NSString *)component8 __attribute__((swift_name("component8()")));
- (NotreDameEvaluationCours *)doCopySession:(NSString *)session dateDebutEvaluation:(double)dateDebutEvaluation dateFinEvaluation:(double)dateFinEvaluation enseignant:(NSString *)enseignant estComplete:(BOOL)estComplete groupe:(NSString *)groupe sigle:(NSString *)sigle typeEvaluation:(NSString *)typeEvaluation __attribute__((swift_name("doCopy(session:dateDebutEvaluation:dateFinEvaluation:enseignant:estComplete:groupe:sigle:typeEvaluation:)")));
@property NSString *session;
@property double dateDebutEvaluation;
@property double dateFinEvaluation;
@property NSString *enseignant;
@property BOOL estComplete;
@property NSString *groupe;
@property NSString *sigle;
@property NSString *typeEvaluation;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("HoraireExamenFinal")))
@interface NotreDameHoraireExamenFinal : KotlinBase
- (instancetype)initWithSigle:(NSString *)sigle groupe:(NSString * _Nullable)groupe dateExamen:(NSString * _Nullable)dateExamen heureDebut:(NSString * _Nullable)heureDebut heureFin:(NSString * _Nullable)heureFin local:(NSString * _Nullable)local __attribute__((swift_name("init(sigle:groupe:dateExamen:heureDebut:heureFin:local:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (NSString * _Nullable)component2 __attribute__((swift_name("component2()")));
- (NSString * _Nullable)component3 __attribute__((swift_name("component3()")));
- (NSString * _Nullable)component4 __attribute__((swift_name("component4()")));
- (NSString * _Nullable)component5 __attribute__((swift_name("component5()")));
- (NSString * _Nullable)component6 __attribute__((swift_name("component6()")));
- (NotreDameHoraireExamenFinal *)doCopySigle:(NSString *)sigle groupe:(NSString * _Nullable)groupe dateExamen:(NSString * _Nullable)dateExamen heureDebut:(NSString * _Nullable)heureDebut heureFin:(NSString * _Nullable)heureFin local:(NSString * _Nullable)local __attribute__((swift_name("doCopy(sigle:groupe:dateExamen:heureDebut:heureFin:local:)")));
@property NSString *sigle;
@property NSString * _Nullable groupe;
@property NSString * _Nullable dateExamen;
@property NSString * _Nullable heureDebut;
@property NSString * _Nullable heureFin;
@property NSString * _Nullable local;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("JourRemplace")))
@interface NotreDameJourRemplace : KotlinBase
- (instancetype)initWithDateOrigine:(NSString *)dateOrigine dateRemplacement:(NSString *)dateRemplacement description:(NSString * _Nullable)description __attribute__((swift_name("init(dateOrigine:dateRemplacement:description:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (NSString *)component2 __attribute__((swift_name("component2()")));
- (NSString * _Nullable)component3 __attribute__((swift_name("component3()")));
- (NotreDameJourRemplace *)doCopyDateOrigine:(NSString *)dateOrigine dateRemplacement:(NSString *)dateRemplacement description:(NSString * _Nullable)description __attribute__((swift_name("doCopy(dateOrigine:dateRemplacement:description:)")));
@property NSString *dateOrigine;
@property NSString *dateRemplacement;
@property (getter=description_) NSString * _Nullable description;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Programme")))
@interface NotreDameProgramme : KotlinBase
- (instancetype)initWithCode:(NSString *)code libelle:(NSString *)libelle profil:(NSString *)profil statut:(NSString *)statut sessionDebut:(NSString *)sessionDebut sessionFin:(NSString *)sessionFin moyenne:(NSString *)moyenne nbEquivalences:(int32_t)nbEquivalences nbCrsReussis:(int32_t)nbCrsReussis nbCrsEchoues:(int32_t)nbCrsEchoues nbCreditsInscrits:(int32_t)nbCreditsInscrits nbCreditsCompletes:(int32_t)nbCreditsCompletes nbCreditsPotentiels:(int32_t)nbCreditsPotentiels nbCreditsRecherche:(int32_t)nbCreditsRecherche __attribute__((swift_name("init(code:libelle:profil:statut:sessionDebut:sessionFin:moyenne:nbEquivalences:nbCrsReussis:nbCrsEchoues:nbCreditsInscrits:nbCreditsCompletes:nbCreditsPotentiels:nbCreditsRecherche:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (NSString *)component2 __attribute__((swift_name("component2()")));
- (NSString *)component3 __attribute__((swift_name("component3()")));
- (NSString *)component4 __attribute__((swift_name("component4()")));
- (NSString *)component5 __attribute__((swift_name("component5()")));
- (NSString *)component6 __attribute__((swift_name("component6()")));
- (NSString *)component7 __attribute__((swift_name("component7()")));
- (int32_t)component8 __attribute__((swift_name("component8()")));
- (int32_t)component9 __attribute__((swift_name("component9()")));
- (int32_t)component10 __attribute__((swift_name("component10()")));
- (int32_t)component11 __attribute__((swift_name("component11()")));
- (int32_t)component12 __attribute__((swift_name("component12()")));
- (int32_t)component13 __attribute__((swift_name("component13()")));
- (int32_t)component14 __attribute__((swift_name("component14()")));
- (NotreDameProgramme *)doCopyCode:(NSString *)code libelle:(NSString *)libelle profil:(NSString *)profil statut:(NSString *)statut sessionDebut:(NSString *)sessionDebut sessionFin:(NSString *)sessionFin moyenne:(NSString *)moyenne nbEquivalences:(int32_t)nbEquivalences nbCrsReussis:(int32_t)nbCrsReussis nbCrsEchoues:(int32_t)nbCrsEchoues nbCreditsInscrits:(int32_t)nbCreditsInscrits nbCreditsCompletes:(int32_t)nbCreditsCompletes nbCreditsPotentiels:(int32_t)nbCreditsPotentiels nbCreditsRecherche:(int32_t)nbCreditsRecherche __attribute__((swift_name("doCopy(code:libelle:profil:statut:sessionDebut:sessionFin:moyenne:nbEquivalences:nbCrsReussis:nbCrsEchoues:nbCreditsInscrits:nbCreditsCompletes:nbCreditsPotentiels:nbCreditsRecherche:)")));
@property NSString *code;
@property NSString *libelle;
@property NSString *profil;
@property NSString *statut;
@property NSString *sessionDebut;
@property NSString *sessionFin;
@property NSString *moyenne;
@property int32_t nbEquivalences;
@property int32_t nbCrsReussis;
@property int32_t nbCrsEchoues;
@property int32_t nbCreditsInscrits;
@property int32_t nbCreditsCompletes;
@property int32_t nbCreditsPotentiels;
@property int32_t nbCreditsRecherche;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Seance")))
@interface NotreDameSeance : KotlinBase
- (instancetype)initWithDateDebut:(double)dateDebut dateFin:(double)dateFin nomActivite:(NSString *)nomActivite local:(NSString *)local descriptionActivite:(NSString *)descriptionActivite libelleCours:(NSString *)libelleCours sigleCours:(NSString *)sigleCours groupe:(NSString *)groupe session:(NSString *)session __attribute__((swift_name("init(dateDebut:dateFin:nomActivite:local:descriptionActivite:libelleCours:sigleCours:groupe:session:)"))) __attribute__((objc_designated_initializer));
- (double)component1 __attribute__((swift_name("component1()")));
- (double)component2 __attribute__((swift_name("component2()")));
- (NSString *)component3 __attribute__((swift_name("component3()")));
- (NSString *)component4 __attribute__((swift_name("component4()")));
- (NSString *)component5 __attribute__((swift_name("component5()")));
- (NSString *)component6 __attribute__((swift_name("component6()")));
- (NSString *)component7 __attribute__((swift_name("component7()")));
- (NSString *)component8 __attribute__((swift_name("component8()")));
- (NSString *)component9 __attribute__((swift_name("component9()")));
- (NotreDameSeance *)doCopyDateDebut:(double)dateDebut dateFin:(double)dateFin nomActivite:(NSString *)nomActivite local:(NSString *)local descriptionActivite:(NSString *)descriptionActivite libelleCours:(NSString *)libelleCours sigleCours:(NSString *)sigleCours groupe:(NSString *)groupe session:(NSString *)session __attribute__((swift_name("doCopy(dateDebut:dateFin:nomActivite:local:descriptionActivite:libelleCours:sigleCours:groupe:session:)")));
@property double dateDebut;
@property double dateFin;
@property NSString *nomActivite;
@property NSString *local;
@property NSString *descriptionActivite;
@property NSString *libelleCours;
@property NSString *sigleCours;
@property NSString *groupe;
@property NSString *session;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Session")))
@interface NotreDameSession : KotlinBase
- (instancetype)initWithAbrege:(NSString *)abrege auLong:(NSString *)auLong dateDebut:(int64_t)dateDebut dateFin:(int64_t)dateFin dateFinCours:(int64_t)dateFinCours dateDebutChemiNot:(int64_t)dateDebutChemiNot dateFinChemiNot:(int64_t)dateFinChemiNot dateDebutAnnulationAvecRemboursement:(int64_t)dateDebutAnnulationAvecRemboursement dateFinAnnulationAvecRemboursement:(int64_t)dateFinAnnulationAvecRemboursement dateFinAnnulationAvecRemboursementNouveauxEtudiants:(int64_t)dateFinAnnulationAvecRemboursementNouveauxEtudiants dateDebutAnnulationSansRemboursementNouveauxEtudiants:(int64_t)dateDebutAnnulationSansRemboursementNouveauxEtudiants dateFinAnnulationSansRemboursementNouveauxEtudiants:(int64_t)dateFinAnnulationSansRemboursementNouveauxEtudiants dateLimitePourAnnulerASEQ:(int64_t)dateLimitePourAnnulerASEQ __attribute__((swift_name("init(abrege:auLong:dateDebut:dateFin:dateFinCours:dateDebutChemiNot:dateFinChemiNot:dateDebutAnnulationAvecRemboursement:dateFinAnnulationAvecRemboursement:dateFinAnnulationAvecRemboursementNouveauxEtudiants:dateDebutAnnulationSansRemboursementNouveauxEtudiants:dateFinAnnulationSansRemboursementNouveauxEtudiants:dateLimitePourAnnulerASEQ:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (NSString *)component2 __attribute__((swift_name("component2()")));
- (int64_t)component3 __attribute__((swift_name("component3()")));
- (int64_t)component4 __attribute__((swift_name("component4()")));
- (int64_t)component5 __attribute__((swift_name("component5()")));
- (int64_t)component6 __attribute__((swift_name("component6()")));
- (int64_t)component7 __attribute__((swift_name("component7()")));
- (int64_t)component8 __attribute__((swift_name("component8()")));
- (int64_t)component9 __attribute__((swift_name("component9()")));
- (int64_t)component10 __attribute__((swift_name("component10()")));
- (int64_t)component11 __attribute__((swift_name("component11()")));
- (int64_t)component12 __attribute__((swift_name("component12()")));
- (int64_t)component13 __attribute__((swift_name("component13()")));
- (NotreDameSession *)doCopyAbrege:(NSString *)abrege auLong:(NSString *)auLong dateDebut:(int64_t)dateDebut dateFin:(int64_t)dateFin dateFinCours:(int64_t)dateFinCours dateDebutChemiNot:(int64_t)dateDebutChemiNot dateFinChemiNot:(int64_t)dateFinChemiNot dateDebutAnnulationAvecRemboursement:(int64_t)dateDebutAnnulationAvecRemboursement dateFinAnnulationAvecRemboursement:(int64_t)dateFinAnnulationAvecRemboursement dateFinAnnulationAvecRemboursementNouveauxEtudiants:(int64_t)dateFinAnnulationAvecRemboursementNouveauxEtudiants dateDebutAnnulationSansRemboursementNouveauxEtudiants:(int64_t)dateDebutAnnulationSansRemboursementNouveauxEtudiants dateFinAnnulationSansRemboursementNouveauxEtudiants:(int64_t)dateFinAnnulationSansRemboursementNouveauxEtudiants dateLimitePourAnnulerASEQ:(int64_t)dateLimitePourAnnulerASEQ __attribute__((swift_name("doCopy(abrege:auLong:dateDebut:dateFin:dateFinCours:dateDebutChemiNot:dateFinChemiNot:dateDebutAnnulationAvecRemboursement:dateFinAnnulationAvecRemboursement:dateFinAnnulationAvecRemboursementNouveauxEtudiants:dateDebutAnnulationSansRemboursementNouveauxEtudiants:dateFinAnnulationSansRemboursementNouveauxEtudiants:dateLimitePourAnnulerASEQ:)")));
@property NSString *abrege;
@property NSString *auLong;
@property int64_t dateDebut;
@property int64_t dateFin;
@property int64_t dateFinCours;
@property int64_t dateDebutChemiNot;
@property int64_t dateFinChemiNot;
@property int64_t dateDebutAnnulationAvecRemboursement;
@property int64_t dateFinAnnulationAvecRemboursement;
@property int64_t dateFinAnnulationAvecRemboursementNouveauxEtudiants;
@property int64_t dateDebutAnnulationSansRemboursementNouveauxEtudiants;
@property int64_t dateFinAnnulationSansRemboursementNouveauxEtudiants;
@property int64_t dateLimitePourAnnulerASEQ;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("SignetsUserCredentials")))
@interface NotreDameSignetsUserCredentials : KotlinBase <NotreDameAndroidParcel>
- (instancetype)initWithCodeAccesUniversel:(NotreDameUniversalCode *)codeAccesUniversel motPasse:(NSString *)motPasse __attribute__((swift_name("init(codeAccesUniversel:motPasse:)"))) __attribute__((objc_designated_initializer));
- (NotreDameUniversalCode *)component1 __attribute__((swift_name("component1()")));
- (NSString *)component2 __attribute__((swift_name("component2()")));
- (NotreDameSignetsUserCredentials *)doCopyCodeAccesUniversel:(NotreDameUniversalCode *)codeAccesUniversel motPasse:(NSString *)motPasse __attribute__((swift_name("doCopy(codeAccesUniversel:motPasse:)")));
@property (readonly) NotreDameUniversalCode *codeAccesUniversel;
@property (readonly) NSString *motPasse;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("SignetsUserCredentials.Companion")))
@interface NotreDameSignetsUserCredentialsCompanion : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property NotreDameSignetsUserCredentials * _Nullable INSTANCE;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("SommaireElementsEvaluation")))
@interface NotreDameSommaireElementsEvaluation : KotlinBase
- (instancetype)initWithSigleCours:(NSString *)sigleCours session:(NSString *)session note:(NSString *)note noteSur:(NSString *)noteSur noteSur100:(NSString *)noteSur100 moyenneClasse:(NSString *)moyenneClasse moyenneClassePourcentage:(NSString *)moyenneClassePourcentage ecartTypeClasse:(NSString *)ecartTypeClasse medianeClasse:(NSString *)medianeClasse rangCentileClasse:(NSString *)rangCentileClasse noteACeJourElementsIndividuels:(NSString *)noteACeJourElementsIndividuels noteSur100PourElementsIndividuels:(NSString *)noteSur100PourElementsIndividuels __attribute__((swift_name("init(sigleCours:session:note:noteSur:noteSur100:moyenneClasse:moyenneClassePourcentage:ecartTypeClasse:medianeClasse:rangCentileClasse:noteACeJourElementsIndividuels:noteSur100PourElementsIndividuels:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (NSString *)component2 __attribute__((swift_name("component2()")));
- (NSString *)component3 __attribute__((swift_name("component3()")));
- (NSString *)component4 __attribute__((swift_name("component4()")));
- (NSString *)component5 __attribute__((swift_name("component5()")));
- (NSString *)component6 __attribute__((swift_name("component6()")));
- (NSString *)component7 __attribute__((swift_name("component7()")));
- (NSString *)component8 __attribute__((swift_name("component8()")));
- (NSString *)component9 __attribute__((swift_name("component9()")));
- (NSString *)component10 __attribute__((swift_name("component10()")));
- (NSString *)component11 __attribute__((swift_name("component11()")));
- (NSString *)component12 __attribute__((swift_name("component12()")));
- (NotreDameSommaireElementsEvaluation *)doCopySigleCours:(NSString *)sigleCours session:(NSString *)session note:(NSString *)note noteSur:(NSString *)noteSur noteSur100:(NSString *)noteSur100 moyenneClasse:(NSString *)moyenneClasse moyenneClassePourcentage:(NSString *)moyenneClassePourcentage ecartTypeClasse:(NSString *)ecartTypeClasse medianeClasse:(NSString *)medianeClasse rangCentileClasse:(NSString *)rangCentileClasse noteACeJourElementsIndividuels:(NSString *)noteACeJourElementsIndividuels noteSur100PourElementsIndividuels:(NSString *)noteSur100PourElementsIndividuels __attribute__((swift_name("doCopy(sigleCours:session:note:noteSur:noteSur100:moyenneClasse:moyenneClassePourcentage:ecartTypeClasse:medianeClasse:rangCentileClasse:noteACeJourElementsIndividuels:noteSur100PourElementsIndividuels:)")));
@property NSString *sigleCours;
@property NSString *session;
@property NSString *note;
@property NSString *noteSur;
@property NSString *noteSur100;
@property NSString *moyenneClasse;
@property NSString *moyenneClassePourcentage;
@property NSString *ecartTypeClasse;
@property NSString *medianeClasse;
@property NSString *rangCentileClasse;
@property NSString *noteACeJourElementsIndividuels;
@property NSString *noteSur100PourElementsIndividuels;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("SommaireEtEvaluations")))
@interface NotreDameSommaireEtEvaluations : KotlinBase
- (instancetype)initWithSommaireElementsEvaluation:(NotreDameSommaireElementsEvaluation *)sommaireElementsEvaluation evaluations:(NSArray<NotreDameEvaluation *> *)evaluations __attribute__((swift_name("init(sommaireElementsEvaluation:evaluations:)"))) __attribute__((objc_designated_initializer));
- (NotreDameSommaireElementsEvaluation *)component1 __attribute__((swift_name("component1()")));
- (NSArray<NotreDameEvaluation *> *)component2 __attribute__((swift_name("component2()")));
- (NotreDameSommaireEtEvaluations *)doCopySommaireElementsEvaluation:(NotreDameSommaireElementsEvaluation *)sommaireElementsEvaluation evaluations:(NSArray<NotreDameEvaluation *> *)evaluations __attribute__((swift_name("doCopy(sommaireElementsEvaluation:evaluations:)")));
@property (readonly) NotreDameSommaireElementsEvaluation *sommaireElementsEvaluation;
@property (readonly) NSArray<NotreDameEvaluation *> *evaluations;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UniversalCode")))
@interface NotreDameUniversalCode : KotlinBase <NotreDameAndroidParcel>
- (instancetype)initWithValue:(NSString *)value __attribute__((swift_name("init(value:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (NotreDameUniversalCode *)doCopyValue:(NSString *)value __attribute__((swift_name("doCopy(value:)")));
@property (readonly) NotreDameUniversalCodeError * _Nullable error;
@property (readonly) NSString *value;
@end;

__attribute__((swift_name("KotlinComparable")))
@protocol NotreDameKotlinComparable
@required
- (int32_t)compareToOther:(id _Nullable)other __attribute__((swift_name("compareTo(other:)")));
@end;

__attribute__((swift_name("KotlinEnum")))
@interface NotreDameKotlinEnum : KotlinBase <NotreDameKotlinComparable>
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer));
- (int32_t)compareToOther:(NotreDameKotlinEnum *)other __attribute__((swift_name("compareTo(other:)")));
@property (readonly) NSString *name;
@property (readonly) int32_t ordinal;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UniversalCode.Error")))
@interface NotreDameUniversalCodeError : NotreDameKotlinEnum
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
@property (class, readonly) NotreDameUniversalCodeError *empty;
@property (class, readonly) NotreDameUniversalCodeError *invalid;
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (int32_t)compareToOther:(NotreDameUniversalCodeError *)other __attribute__((swift_name("compareTo(other:)")));
@end;

__attribute__((swift_name("KotlinNumber")))
@interface NotreDameKotlinNumber : KotlinBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (int8_t)toByte __attribute__((swift_name("toByte()")));
- (unichar)toChar __attribute__((swift_name("toChar()")));
- (double)toDouble __attribute__((swift_name("toDouble()")));
- (float)toFloat __attribute__((swift_name("toFloat()")));
- (int32_t)toInt __attribute__((swift_name("toInt()")));
- (int64_t)toLong __attribute__((swift_name("toLong()")));
- (int16_t)toShort __attribute__((swift_name("toShort()")));
@end;

@interface NotreDameKotlinNumber (Extensions)
- (NSString *)formatFractionDigitsMaximumIntegerDigits:(int32_t)maximumIntegerDigits __attribute__((swift_name("formatFractionDigits(maximumIntegerDigits:)")));
- (NSString *)formatSingleFractionDigits __attribute__((swift_name("formatSingleFractionDigits()")));
@end;

NS_ASSUME_NONNULL_END

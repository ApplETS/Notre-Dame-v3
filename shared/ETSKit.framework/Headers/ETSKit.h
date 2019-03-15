#import <Foundation/Foundation.h>

@class ETSKDashboardViewModel, ETSKViewModel, ETSKDashboardCardsUseCase, ETSKDashboardCardRepository, ETSKDashboardCardQueries, ETSKDashboardCard, ETSKDb, ETSKEtsMobileDb, ETSKEtsMobileDispatchers, ETSKKotlinx_coroutines_core_nativeCoroutineDispatcher, ETSKActivite, ETSKCours, ETSKDashboardCardType, ETSKKotlinEnum, ETSKEnseignant, ETSKEtudiant, ETSKEvaluation, ETSKEvaluationCours, ETSKHoraireExamenFinal, ETSKJourRemplace, ETSKProgramme, ETSKSeance, ETSKSession, ETSKSignetsUserCredentials, ETSKUniversalCode, ETSKSignetsUserCredentialsCompanion, ETSKSommaireElementsEvaluation, ETSKSommaireEtEvaluations, ETSKUniversalCodeError, ETSKDashboardCardEntityAdapter, ETSKDashboardCardEntityImpl, ETSKSqldelight_runtimeTransacter, ETSKSqldelight_runtimeQuery, ETSKKotlinUnit, ETSKSqldelight_runtimeTransacterTransaction, ETSKEtsMobileDbSchema, ETSKKotlinNumber, ETSKKotlinThrowable, ETSKKotlinx_coroutines_core_nativeCancellationException, ETSKKotlinAbstractCoroutineContextElement, ETSKKotlinByteArray, ETSKKotlinArray, ETSKKotlinIllegalStateException, ETSKKotlinRuntimeException, ETSKKotlinException, ETSKKotlinByteIterator, ETSKKotlinx_coroutines_core_nativeAtomicDesc, ETSKKotlinx_coroutines_core_nativeAtomicOp, ETSKKotlinx_coroutines_core_nativeOpDescriptor;

@protocol ETSKKotlinx_coroutines_core_nativeCoroutineScope, ETSKKotlinx_coroutines_core_nativeJob, ETSKKotlinx_coroutines_core_nativeChannel, ETSKKotlinx_coroutines_core_nativeReceiveChannel, ETSKSqldelight_runtimeSqlDriver, ETSKAndroidParcel, ETSKKotlinComparable, ETSKDashboardCardEntity, ETSKSqldelight_runtimeColumnAdapter, ETSKSqldelight_runtimeSqlDriverSchema, ETSKSqldelight_runtimeSqlCursor, ETSKSqldelight_runtimeQueryListener, ETSKKotlinCoroutineContext, ETSKKotlinx_coroutines_core_nativeChildHandle, ETSKKotlinx_coroutines_core_nativeChildJob, ETSKKotlinx_coroutines_core_nativeDisposableHandle, ETSKKotlinSequence, ETSKKotlinx_coroutines_core_nativeSelectClause0, ETSKKotlinCoroutineContextElement, ETSKKotlinCoroutineContextKey, ETSKKotlinx_coroutines_core_nativeSendChannel, ETSKKotlinx_coroutines_core_nativeSelectClause2, ETSKKotlinx_coroutines_core_nativeChannelIterator, ETSKKotlinx_coroutines_core_nativeSelectClause1, ETSKSqldelight_runtimeSqlPreparedStatement, ETSKSqldelight_runtimeCloseable, ETSKKotlinContinuationInterceptor, ETSKKotlinContinuation, ETSKKotlinx_coroutines_core_nativeRunnable, ETSKKotlinx_coroutines_core_nativeParentJob, ETSKKotlinIterator, ETSKKotlinx_coroutines_core_nativeSelectInstance, ETSKKotlinSuspendFunction0, ETSKKotlinSuspendFunction1, ETSKKotlinSuspendFunction;

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
@interface ETSKMutableSet<ObjectType> : NSMutableSet<ObjectType>
@end;

__attribute__((objc_runtime_name("KotlinMutableDictionary")))
__attribute__((swift_name("KotlinMutableDictionary")))
@interface ETSKMutableDictionary<KeyType, ObjectType> : NSMutableDictionary<KeyType, ObjectType>
@end;

@interface NSError (NSErrorKotlinException)
@property (readonly) id _Nullable kotlinException;
@end;

__attribute__((objc_runtime_name("KotlinNumber")))
__attribute__((swift_name("KotlinNumber")))
@interface ETSKNumber : NSNumber
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
@interface ETSKByte : ETSKNumber
- (instancetype)initWithChar:(char)value;
+ (instancetype)numberWithChar:(char)value;
@end;

__attribute__((objc_runtime_name("KotlinUByte")))
__attribute__((swift_name("KotlinUByte")))
@interface ETSKUByte : ETSKNumber
- (instancetype)initWithUnsignedChar:(unsigned char)value;
+ (instancetype)numberWithUnsignedChar:(unsigned char)value;
@end;

__attribute__((objc_runtime_name("KotlinShort")))
__attribute__((swift_name("KotlinShort")))
@interface ETSKShort : ETSKNumber
- (instancetype)initWithShort:(short)value;
+ (instancetype)numberWithShort:(short)value;
@end;

__attribute__((objc_runtime_name("KotlinUShort")))
__attribute__((swift_name("KotlinUShort")))
@interface ETSKUShort : ETSKNumber
- (instancetype)initWithUnsignedShort:(unsigned short)value;
+ (instancetype)numberWithUnsignedShort:(unsigned short)value;
@end;

__attribute__((objc_runtime_name("KotlinInt")))
__attribute__((swift_name("KotlinInt")))
@interface ETSKInt : ETSKNumber
- (instancetype)initWithInt:(int)value;
+ (instancetype)numberWithInt:(int)value;
@end;

__attribute__((objc_runtime_name("KotlinUInt")))
__attribute__((swift_name("KotlinUInt")))
@interface ETSKUInt : ETSKNumber
- (instancetype)initWithUnsignedInt:(unsigned int)value;
+ (instancetype)numberWithUnsignedInt:(unsigned int)value;
@end;

__attribute__((objc_runtime_name("KotlinLong")))
__attribute__((swift_name("KotlinLong")))
@interface ETSKLong : ETSKNumber
- (instancetype)initWithLongLong:(long long)value;
+ (instancetype)numberWithLongLong:(long long)value;
@end;

__attribute__((objc_runtime_name("KotlinULong")))
__attribute__((swift_name("KotlinULong")))
@interface ETSKULong : ETSKNumber
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value;
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value;
@end;

__attribute__((objc_runtime_name("KotlinFloat")))
__attribute__((swift_name("KotlinFloat")))
@interface ETSKFloat : ETSKNumber
- (instancetype)initWithFloat:(float)value;
+ (instancetype)numberWithFloat:(float)value;
@end;

__attribute__((objc_runtime_name("KotlinDouble")))
__attribute__((swift_name("KotlinDouble")))
@interface ETSKDouble : ETSKNumber
- (instancetype)initWithDouble:(double)value;
+ (instancetype)numberWithDouble:(double)value;
@end;

__attribute__((objc_runtime_name("KotlinBoolean")))
__attribute__((swift_name("KotlinBoolean")))
@interface ETSKBoolean : ETSKNumber
- (instancetype)initWithBool:(BOOL)value;
+ (instancetype)numberWithBool:(BOOL)value;
@end;

__attribute__((swift_name("ViewModel")))
@interface ETSKViewModel : KotlinBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (readonly) id<ETSKKotlinx_coroutines_core_nativeCoroutineScope> scope;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DashboardViewModel")))
@interface ETSKDashboardViewModel : ETSKViewModel
- (instancetype)initWithDashboardCardsUseCase:(ETSKDashboardCardsUseCase *)dashboardCardsUseCase __attribute__((swift_name("init(dashboardCardsUseCase:)"))) __attribute__((objc_designated_initializer));
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
- (id<ETSKKotlinx_coroutines_core_nativeJob>)load __attribute__((swift_name("load()")));
- (void)onCardMovedFromPosition:(int32_t)fromPosition toPosition:(int32_t)toPosition __attribute__((swift_name("onCardMoved(fromPosition:toPosition:)")));
- (void)onCardRemovedPosition:(int32_t)position __attribute__((swift_name("onCardRemoved(position:)")));
- (void)undoLastRemove __attribute__((swift_name("undoLastRemove()")));
- (void)save __attribute__((swift_name("save()")));
- (void)restore __attribute__((swift_name("restore()")));
@property (readonly) id<ETSKKotlinx_coroutines_core_nativeChannel> cardsChannel;
@property (readonly) id<ETSKKotlinx_coroutines_core_nativeChannel> showUndoRemoveChannel;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DashboardCardRepository")))
@interface ETSKDashboardCardRepository : KotlinBase
- (instancetype)initWithDashboardCardQueries:(ETSKDashboardCardQueries *)dashboardCardQueries __attribute__((swift_name("init(dashboardCardQueries:)"))) __attribute__((objc_designated_initializer));
- (id<ETSKKotlinx_coroutines_core_nativeReceiveChannel>)dashboardCards __attribute__((swift_name("dashboardCards()")));
- (id<ETSKKotlinx_coroutines_core_nativeJob>)updateDashboardCardCard:(ETSKDashboardCard *)card position:(int32_t)position __attribute__((swift_name("updateDashboardCard(card:position:)")));
- (void)restore __attribute__((swift_name("restore()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Db")))
@interface ETSKDb : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)db __attribute__((swift_name("init()")));
- (void)setupDbDriver:(id<ETSKSqldelight_runtimeSqlDriver>)driver __attribute__((swift_name("setupDb(driver:)")));
@property (readonly) BOOL ready;
@property (readonly) ETSKEtsMobileDb *instance;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DashboardCardsUseCase")))
@interface ETSKDashboardCardsUseCase : KotlinBase
- (instancetype)initWithRepository:(ETSKDashboardCardRepository *)repository __attribute__((swift_name("init(repository:)"))) __attribute__((objc_designated_initializer));
- (id<ETSKKotlinx_coroutines_core_nativeReceiveChannel>)fetch __attribute__((swift_name("fetch()")));
- (void)saveVisibleCards:(NSArray<ETSKDashboardCard *> *)visibleCards hiddenCards:(NSArray<ETSKDashboardCard *> *)hiddenCards __attribute__((swift_name("save(visibleCards:hiddenCards:)")));
- (void)restore __attribute__((swift_name("restore()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("EtsMobileDispatchers")))
@interface ETSKEtsMobileDispatchers : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)etsMobileDispatchers __attribute__((swift_name("init()")));
@property (readonly) ETSKKotlinx_coroutines_core_nativeCoroutineDispatcher *Default;
@property (readonly) ETSKKotlinx_coroutines_core_nativeCoroutineDispatcher *IO;
@property (readonly) ETSKKotlinx_coroutines_core_nativeCoroutineDispatcher *Main;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Activite")))
@interface ETSKActivite : KotlinBase
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
- (ETSKActivite *)doCopySigle:(NSString *)sigle groupe:(NSString * _Nullable)groupe jour:(int32_t)jour journee:(NSString * _Nullable)journee codeActivite:(NSString * _Nullable)codeActivite nomActivite:(NSString * _Nullable)nomActivite activitePrincipale:(NSString * _Nullable)activitePrincipale heureDebut:(NSString * _Nullable)heureDebut heureFin:(NSString * _Nullable)heureFin local:(NSString * _Nullable)local titreCours:(NSString * _Nullable)titreCours __attribute__((swift_name("doCopy(sigle:groupe:jour:journee:codeActivite:nomActivite:activitePrincipale:heureDebut:heureFin:local:titreCours:)")));
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
@protocol ETSKAndroidParcel
@required
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Cours")))
@interface ETSKCours : KotlinBase <ETSKAndroidParcel>
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
- (ETSKCours *)doCopySigle:(NSString *)sigle groupe:(NSString *)groupe session:(NSString *)session programmeEtudes:(NSString *)programmeEtudes cote:(NSString * _Nullable)cote noteSur100:(NSString * _Nullable)noteSur100 nbCredits:(int32_t)nbCredits titreCours:(NSString *)titreCours __attribute__((swift_name("doCopy(sigle:groupe:session:programmeEtudes:cote:noteSur100:nbCredits:titreCours:)")));
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
__attribute__((swift_name("DashboardCard")))
@interface ETSKDashboardCard : KotlinBase
- (instancetype)initWithType:(ETSKDashboardCardType *)type visible:(BOOL)visible dismissible:(BOOL)dismissible __attribute__((swift_name("init(type:visible:dismissible:)"))) __attribute__((objc_designated_initializer));
- (ETSKDashboardCardType *)component1 __attribute__((swift_name("component1()")));
- (BOOL)component2 __attribute__((swift_name("component2()")));
- (BOOL)component3 __attribute__((swift_name("component3()")));
- (ETSKDashboardCard *)doCopyType:(ETSKDashboardCardType *)type visible:(BOOL)visible dismissible:(BOOL)dismissible __attribute__((swift_name("doCopy(type:visible:dismissible:)")));
@property (readonly) ETSKDashboardCardType *type;
@property BOOL visible;
@property (readonly) BOOL dismissible;
@end;

__attribute__((swift_name("KotlinComparable")))
@protocol ETSKKotlinComparable
@required
- (int32_t)compareToOther:(id _Nullable)other __attribute__((swift_name("compareTo(other:)")));
@end;

__attribute__((swift_name("KotlinEnum")))
@interface ETSKKotlinEnum : KotlinBase <ETSKKotlinComparable>
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer));
- (int32_t)compareToOther:(ETSKKotlinEnum *)other __attribute__((swift_name("compareTo(other:)")));
@property (readonly) NSString *name;
@property (readonly) int32_t ordinal;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DashboardCard.Type")))
@interface ETSKDashboardCardType : ETSKKotlinEnum
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
@property (class, readonly) ETSKDashboardCardType *dashboardCardApplets;
@property (class, readonly) ETSKDashboardCardType *dashboardCardTodaySchedule;
@property (class, readonly) ETSKDashboardCardType *dashboardCardGrades;
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (int32_t)compareToOther:(ETSKDashboardCardType *)other __attribute__((swift_name("compareTo(other:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Enseignant")))
@interface ETSKEnseignant : KotlinBase
- (instancetype)initWithLocalBureau:(NSString * _Nullable)localBureau telephone:(NSString * _Nullable)telephone enseignantPrincipal:(NSString * _Nullable)enseignantPrincipal nom:(NSString * _Nullable)nom prenom:(NSString * _Nullable)prenom courriel:(NSString *)courriel __attribute__((swift_name("init(localBureau:telephone:enseignantPrincipal:nom:prenom:courriel:)"))) __attribute__((objc_designated_initializer));
- (NSString * _Nullable)component1 __attribute__((swift_name("component1()")));
- (NSString * _Nullable)component2 __attribute__((swift_name("component2()")));
- (NSString * _Nullable)component3 __attribute__((swift_name("component3()")));
- (NSString * _Nullable)component4 __attribute__((swift_name("component4()")));
- (NSString * _Nullable)component5 __attribute__((swift_name("component5()")));
- (NSString *)component6 __attribute__((swift_name("component6()")));
- (ETSKEnseignant *)doCopyLocalBureau:(NSString * _Nullable)localBureau telephone:(NSString * _Nullable)telephone enseignantPrincipal:(NSString * _Nullable)enseignantPrincipal nom:(NSString * _Nullable)nom prenom:(NSString * _Nullable)prenom courriel:(NSString *)courriel __attribute__((swift_name("doCopy(localBureau:telephone:enseignantPrincipal:nom:prenom:courriel:)")));
@property NSString * _Nullable localBureau;
@property NSString * _Nullable telephone;
@property NSString * _Nullable enseignantPrincipal;
@property NSString * _Nullable nom;
@property NSString * _Nullable prenom;
@property NSString *courriel;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Etudiant")))
@interface ETSKEtudiant : KotlinBase
- (instancetype)initWithType:(NSString *)type nom:(NSString *)nom prenom:(NSString *)prenom codePerm:(NSString *)codePerm soldeTotal:(NSString *)soldeTotal masculin:(BOOL)masculin __attribute__((swift_name("init(type:nom:prenom:codePerm:soldeTotal:masculin:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (NSString *)component2 __attribute__((swift_name("component2()")));
- (NSString *)component3 __attribute__((swift_name("component3()")));
- (NSString *)component4 __attribute__((swift_name("component4()")));
- (NSString *)component5 __attribute__((swift_name("component5()")));
- (BOOL)component6 __attribute__((swift_name("component6()")));
- (ETSKEtudiant *)doCopyType:(NSString *)type nom:(NSString *)nom prenom:(NSString *)prenom codePerm:(NSString *)codePerm soldeTotal:(NSString *)soldeTotal masculin:(BOOL)masculin __attribute__((swift_name("doCopy(type:nom:prenom:codePerm:soldeTotal:masculin:)")));
@property NSString *type;
@property NSString *nom;
@property NSString *prenom;
@property NSString *codePerm;
@property NSString *soldeTotal;
@property BOOL masculin;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Evaluation")))
@interface ETSKEvaluation : KotlinBase
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
- (ETSKEvaluation *)doCopyCours:(NSString *)cours groupe:(NSString *)groupe session:(NSString *)session nom:(NSString *)nom equipe:(NSString *)equipe dateCible:(id _Nullable)dateCible note:(NSString *)note corrigeSur:(NSString *)corrigeSur notePourcentage:(NSString *)notePourcentage ponderation:(NSString *)ponderation moyenne:(NSString *)moyenne moyennePourcentage:(NSString *)moyennePourcentage ecartType:(NSString *)ecartType mediane:(NSString *)mediane rangCentile:(NSString *)rangCentile publie:(BOOL)publie messageDuProf:(NSString *)messageDuProf ignoreDuCalcul:(BOOL)ignoreDuCalcul __attribute__((swift_name("doCopy(cours:groupe:session:nom:equipe:dateCible:note:corrigeSur:notePourcentage:ponderation:moyenne:moyennePourcentage:ecartType:mediane:rangCentile:publie:messageDuProf:ignoreDuCalcul:)")));
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
@interface ETSKEvaluationCours : KotlinBase
- (instancetype)initWithSession:(NSString *)session dateDebutEvaluation:(double)dateDebutEvaluation dateFinEvaluation:(double)dateFinEvaluation enseignant:(NSString *)enseignant estComplete:(BOOL)estComplete groupe:(NSString *)groupe sigle:(NSString *)sigle typeEvaluation:(NSString *)typeEvaluation __attribute__((swift_name("init(session:dateDebutEvaluation:dateFinEvaluation:enseignant:estComplete:groupe:sigle:typeEvaluation:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (double)component2 __attribute__((swift_name("component2()")));
- (double)component3 __attribute__((swift_name("component3()")));
- (NSString *)component4 __attribute__((swift_name("component4()")));
- (BOOL)component5 __attribute__((swift_name("component5()")));
- (NSString *)component6 __attribute__((swift_name("component6()")));
- (NSString *)component7 __attribute__((swift_name("component7()")));
- (NSString *)component8 __attribute__((swift_name("component8()")));
- (ETSKEvaluationCours *)doCopySession:(NSString *)session dateDebutEvaluation:(double)dateDebutEvaluation dateFinEvaluation:(double)dateFinEvaluation enseignant:(NSString *)enseignant estComplete:(BOOL)estComplete groupe:(NSString *)groupe sigle:(NSString *)sigle typeEvaluation:(NSString *)typeEvaluation __attribute__((swift_name("doCopy(session:dateDebutEvaluation:dateFinEvaluation:enseignant:estComplete:groupe:sigle:typeEvaluation:)")));
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
@interface ETSKHoraireExamenFinal : KotlinBase
- (instancetype)initWithSigle:(NSString *)sigle groupe:(NSString * _Nullable)groupe dateExamen:(NSString * _Nullable)dateExamen heureDebut:(NSString * _Nullable)heureDebut heureFin:(NSString * _Nullable)heureFin local:(NSString * _Nullable)local __attribute__((swift_name("init(sigle:groupe:dateExamen:heureDebut:heureFin:local:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (NSString * _Nullable)component2 __attribute__((swift_name("component2()")));
- (NSString * _Nullable)component3 __attribute__((swift_name("component3()")));
- (NSString * _Nullable)component4 __attribute__((swift_name("component4()")));
- (NSString * _Nullable)component5 __attribute__((swift_name("component5()")));
- (NSString * _Nullable)component6 __attribute__((swift_name("component6()")));
- (ETSKHoraireExamenFinal *)doCopySigle:(NSString *)sigle groupe:(NSString * _Nullable)groupe dateExamen:(NSString * _Nullable)dateExamen heureDebut:(NSString * _Nullable)heureDebut heureFin:(NSString * _Nullable)heureFin local:(NSString * _Nullable)local __attribute__((swift_name("doCopy(sigle:groupe:dateExamen:heureDebut:heureFin:local:)")));
@property NSString *sigle;
@property NSString * _Nullable groupe;
@property NSString * _Nullable dateExamen;
@property NSString * _Nullable heureDebut;
@property NSString * _Nullable heureFin;
@property NSString * _Nullable local;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("JourRemplace")))
@interface ETSKJourRemplace : KotlinBase
- (instancetype)initWithDateOrigine:(NSString *)dateOrigine dateRemplacement:(NSString *)dateRemplacement description:(NSString * _Nullable)description __attribute__((swift_name("init(dateOrigine:dateRemplacement:description:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (NSString *)component2 __attribute__((swift_name("component2()")));
- (NSString * _Nullable)component3 __attribute__((swift_name("component3()")));
- (ETSKJourRemplace *)doCopyDateOrigine:(NSString *)dateOrigine dateRemplacement:(NSString *)dateRemplacement description:(NSString * _Nullable)description __attribute__((swift_name("doCopy(dateOrigine:dateRemplacement:description:)")));
@property NSString *dateOrigine;
@property NSString *dateRemplacement;
@property (getter=description_) NSString * _Nullable description;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Programme")))
@interface ETSKProgramme : KotlinBase
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
- (ETSKProgramme *)doCopyCode:(NSString *)code libelle:(NSString *)libelle profil:(NSString *)profil statut:(NSString *)statut sessionDebut:(NSString *)sessionDebut sessionFin:(NSString *)sessionFin moyenne:(NSString *)moyenne nbEquivalences:(int32_t)nbEquivalences nbCrsReussis:(int32_t)nbCrsReussis nbCrsEchoues:(int32_t)nbCrsEchoues nbCreditsInscrits:(int32_t)nbCreditsInscrits nbCreditsCompletes:(int32_t)nbCreditsCompletes nbCreditsPotentiels:(int32_t)nbCreditsPotentiels nbCreditsRecherche:(int32_t)nbCreditsRecherche __attribute__((swift_name("doCopy(code:libelle:profil:statut:sessionDebut:sessionFin:moyenne:nbEquivalences:nbCrsReussis:nbCrsEchoues:nbCreditsInscrits:nbCreditsCompletes:nbCreditsPotentiels:nbCreditsRecherche:)")));
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
@interface ETSKSeance : KotlinBase
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
- (ETSKSeance *)doCopyDateDebut:(double)dateDebut dateFin:(double)dateFin nomActivite:(NSString *)nomActivite local:(NSString *)local descriptionActivite:(NSString *)descriptionActivite libelleCours:(NSString *)libelleCours sigleCours:(NSString *)sigleCours groupe:(NSString *)groupe session:(NSString *)session __attribute__((swift_name("doCopy(dateDebut:dateFin:nomActivite:local:descriptionActivite:libelleCours:sigleCours:groupe:session:)")));
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
@interface ETSKSession : KotlinBase
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
- (ETSKSession *)doCopyAbrege:(NSString *)abrege auLong:(NSString *)auLong dateDebut:(int64_t)dateDebut dateFin:(int64_t)dateFin dateFinCours:(int64_t)dateFinCours dateDebutChemiNot:(int64_t)dateDebutChemiNot dateFinChemiNot:(int64_t)dateFinChemiNot dateDebutAnnulationAvecRemboursement:(int64_t)dateDebutAnnulationAvecRemboursement dateFinAnnulationAvecRemboursement:(int64_t)dateFinAnnulationAvecRemboursement dateFinAnnulationAvecRemboursementNouveauxEtudiants:(int64_t)dateFinAnnulationAvecRemboursementNouveauxEtudiants dateDebutAnnulationSansRemboursementNouveauxEtudiants:(int64_t)dateDebutAnnulationSansRemboursementNouveauxEtudiants dateFinAnnulationSansRemboursementNouveauxEtudiants:(int64_t)dateFinAnnulationSansRemboursementNouveauxEtudiants dateLimitePourAnnulerASEQ:(int64_t)dateLimitePourAnnulerASEQ __attribute__((swift_name("doCopy(abrege:auLong:dateDebut:dateFin:dateFinCours:dateDebutChemiNot:dateFinChemiNot:dateDebutAnnulationAvecRemboursement:dateFinAnnulationAvecRemboursement:dateFinAnnulationAvecRemboursementNouveauxEtudiants:dateDebutAnnulationSansRemboursementNouveauxEtudiants:dateFinAnnulationSansRemboursementNouveauxEtudiants:dateLimitePourAnnulerASEQ:)")));
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
@interface ETSKSignetsUserCredentials : KotlinBase <ETSKAndroidParcel>
- (instancetype)initWithCodeAccesUniversel:(ETSKUniversalCode *)codeAccesUniversel motPasse:(NSString *)motPasse __attribute__((swift_name("init(codeAccesUniversel:motPasse:)"))) __attribute__((objc_designated_initializer));
- (ETSKUniversalCode *)component1 __attribute__((swift_name("component1()")));
- (NSString *)component2 __attribute__((swift_name("component2()")));
- (ETSKSignetsUserCredentials *)doCopyCodeAccesUniversel:(ETSKUniversalCode *)codeAccesUniversel motPasse:(NSString *)motPasse __attribute__((swift_name("doCopy(codeAccesUniversel:motPasse:)")));
@property (readonly) ETSKUniversalCode *codeAccesUniversel;
@property (readonly) NSString *motPasse;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("SignetsUserCredentials.Companion")))
@interface ETSKSignetsUserCredentialsCompanion : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property ETSKSignetsUserCredentials * _Nullable INSTANCE;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("SommaireElementsEvaluation")))
@interface ETSKSommaireElementsEvaluation : KotlinBase
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
- (ETSKSommaireElementsEvaluation *)doCopySigleCours:(NSString *)sigleCours session:(NSString *)session note:(NSString *)note noteSur:(NSString *)noteSur noteSur100:(NSString *)noteSur100 moyenneClasse:(NSString *)moyenneClasse moyenneClassePourcentage:(NSString *)moyenneClassePourcentage ecartTypeClasse:(NSString *)ecartTypeClasse medianeClasse:(NSString *)medianeClasse rangCentileClasse:(NSString *)rangCentileClasse noteACeJourElementsIndividuels:(NSString *)noteACeJourElementsIndividuels noteSur100PourElementsIndividuels:(NSString *)noteSur100PourElementsIndividuels __attribute__((swift_name("doCopy(sigleCours:session:note:noteSur:noteSur100:moyenneClasse:moyenneClassePourcentage:ecartTypeClasse:medianeClasse:rangCentileClasse:noteACeJourElementsIndividuels:noteSur100PourElementsIndividuels:)")));
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
@interface ETSKSommaireEtEvaluations : KotlinBase
- (instancetype)initWithSommaireElementsEvaluation:(ETSKSommaireElementsEvaluation *)sommaireElementsEvaluation evaluations:(NSArray<ETSKEvaluation *> *)evaluations __attribute__((swift_name("init(sommaireElementsEvaluation:evaluations:)"))) __attribute__((objc_designated_initializer));
- (ETSKSommaireElementsEvaluation *)component1 __attribute__((swift_name("component1()")));
- (NSArray<ETSKEvaluation *> *)component2 __attribute__((swift_name("component2()")));
- (ETSKSommaireEtEvaluations *)doCopySommaireElementsEvaluation:(ETSKSommaireElementsEvaluation *)sommaireElementsEvaluation evaluations:(NSArray<ETSKEvaluation *> *)evaluations __attribute__((swift_name("doCopy(sommaireElementsEvaluation:evaluations:)")));
@property (readonly) ETSKSommaireElementsEvaluation *sommaireElementsEvaluation;
@property (readonly) NSArray<ETSKEvaluation *> *evaluations;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UniversalCode")))
@interface ETSKUniversalCode : KotlinBase <ETSKAndroidParcel>
- (instancetype)initWithValue:(NSString *)value __attribute__((swift_name("init(value:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (ETSKUniversalCode *)doCopyValue:(NSString *)value __attribute__((swift_name("doCopy(value:)")));
@property (readonly) ETSKUniversalCodeError * _Nullable error;
@property (readonly) NSString *value;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UniversalCode.Error")))
@interface ETSKUniversalCodeError : ETSKKotlinEnum
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
@property (class, readonly) ETSKUniversalCodeError *empty;
@property (class, readonly) ETSKUniversalCodeError *invalid;
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (int32_t)compareToOther:(ETSKUniversalCodeError *)other __attribute__((swift_name("compareTo(other:)")));
@end;

__attribute__((swift_name("DashboardCardEntity")))
@protocol ETSKDashboardCardEntity
@required
@property (readonly) ETSKDashboardCardType *type;
@property (readonly) int64_t pos;
@property (readonly) BOOL visible;
@property (readonly) BOOL dismissible;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DashboardCardEntityAdapter")))
@interface ETSKDashboardCardEntityAdapter : KotlinBase
- (instancetype)initWithTypeAdapter:(id<ETSKSqldelight_runtimeColumnAdapter>)typeAdapter __attribute__((swift_name("init(typeAdapter:)"))) __attribute__((objc_designated_initializer));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DashboardCardEntityImpl")))
@interface ETSKDashboardCardEntityImpl : KotlinBase <ETSKDashboardCardEntity>
- (instancetype)initWithType:(ETSKDashboardCardType *)type pos:(int64_t)pos visible:(BOOL)visible dismissible:(BOOL)dismissible __attribute__((swift_name("init(type:pos:visible:dismissible:)"))) __attribute__((objc_designated_initializer));
- (ETSKDashboardCardType *)component1 __attribute__((swift_name("component1()")));
- (int64_t)component2 __attribute__((swift_name("component2()")));
- (BOOL)component3 __attribute__((swift_name("component3()")));
- (BOOL)component4 __attribute__((swift_name("component4()")));
- (ETSKDashboardCardEntityImpl *)doCopyType:(ETSKDashboardCardType *)type pos:(int64_t)pos visible:(BOOL)visible dismissible:(BOOL)dismissible __attribute__((swift_name("doCopy(type:pos:visible:dismissible:)")));
@end;

__attribute__((swift_name("Sqldelight_runtimeTransacter")))
@interface ETSKSqldelight_runtimeTransacter : KotlinBase
- (instancetype)initWithDriver:(id<ETSKSqldelight_runtimeSqlDriver>)driver __attribute__((swift_name("init(driver:)"))) __attribute__((objc_designated_initializer));
- (NSString *)createArgumentsCount:(int32_t)count offset:(int32_t)offset __attribute__((swift_name("createArguments(count:offset:)")));
- (void)notifyQueriesQueryList:(NSArray<ETSKSqldelight_runtimeQuery *> *)queryList __attribute__((swift_name("notifyQueries(queryList:)")));
- (void)transactionNoEnclosing:(BOOL)noEnclosing body:(ETSKKotlinUnit *(^)(ETSKSqldelight_runtimeTransacterTransaction *))body __attribute__((swift_name("transaction(noEnclosing:body:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DashboardCardQueries")))
@interface ETSKDashboardCardQueries : ETSKSqldelight_runtimeTransacter
- (instancetype)initWithDatabase:(ETSKEtsMobileDb *)database driver:(id<ETSKSqldelight_runtimeSqlDriver>)driver __attribute__((swift_name("init(database:driver:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithDriver:(id<ETSKSqldelight_runtimeSqlDriver>)driver __attribute__((swift_name("init(driver:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (ETSKSqldelight_runtimeQuery *)selectAllMapper:(id (^)(ETSKDashboardCardType *, ETSKLong *, ETSKBoolean *, ETSKBoolean *))mapper __attribute__((swift_name("selectAll(mapper:)")));
- (ETSKSqldelight_runtimeQuery *)selectAll __attribute__((swift_name("selectAll()")));
- (void)insertInitialCards __attribute__((swift_name("insertInitialCards()")));
- (void)updateCardPos:(int64_t)pos visible:(BOOL)visible type:(ETSKDashboardCardType *)type __attribute__((swift_name("updateCard(pos:visible:type:)")));
- (void)deleteAll __attribute__((swift_name("deleteAll()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("EtsMobileDb")))
@interface ETSKEtsMobileDb : ETSKSqldelight_runtimeTransacter
- (instancetype)initWithDriver:(id<ETSKSqldelight_runtimeSqlDriver>)driver dashboardCardEntityAdapter:(ETSKDashboardCardEntityAdapter *)dashboardCardEntityAdapter __attribute__((swift_name("init(driver:dashboardCardEntityAdapter:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithDriver:(id<ETSKSqldelight_runtimeSqlDriver>)driver __attribute__((swift_name("init(driver:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (readonly) ETSKDashboardCardQueries *dashboardCardQueries;
@end;

__attribute__((swift_name("Sqldelight_runtimeSqlDriverSchema")))
@protocol ETSKSqldelight_runtimeSqlDriverSchema
@required
- (void)createDriver:(id<ETSKSqldelight_runtimeSqlDriver>)driver __attribute__((swift_name("create(driver:)")));
- (void)migrateDriver:(id<ETSKSqldelight_runtimeSqlDriver>)driver oldVersion:(int32_t)oldVersion newVersion:(int32_t)newVersion __attribute__((swift_name("migrate(driver:oldVersion:newVersion:)")));
@property (readonly) int32_t version;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("EtsMobileDb.Schema")))
@interface ETSKEtsMobileDbSchema : KotlinBase <ETSKSqldelight_runtimeSqlDriverSchema>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)schema __attribute__((swift_name("init()")));
@end;

__attribute__((swift_name("KotlinNumber")))
@interface ETSKKotlinNumber : KotlinBase
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

@interface ETSKKotlinNumber (Extensions)
- (NSString *)formatFractionDigitsMaximumIntegerDigits:(int32_t)maximumIntegerDigits __attribute__((swift_name("formatFractionDigits(maximumIntegerDigits:)")));
- (NSString *)formatSingleFractionDigits __attribute__((swift_name("formatSingleFractionDigits()")));
@end;

__attribute__((swift_name("Sqldelight_runtimeQuery")))
@interface ETSKSqldelight_runtimeQuery : KotlinBase
- (instancetype)initWithQueries:(NSMutableArray<ETSKSqldelight_runtimeQuery *> *)queries mapper:(id (^)(id<ETSKSqldelight_runtimeSqlCursor>))mapper __attribute__((swift_name("init(queries:mapper:)"))) __attribute__((objc_designated_initializer));
- (void)addListenerListener:(id<ETSKSqldelight_runtimeQueryListener>)listener __attribute__((swift_name("addListener(listener:)")));
- (id<ETSKSqldelight_runtimeSqlCursor>)execute __attribute__((swift_name("execute()")));
- (NSArray<id> *)executeAsList __attribute__((swift_name("executeAsList()")));
- (id)executeAsOne __attribute__((swift_name("executeAsOne()")));
- (id _Nullable)executeAsOneOrNull __attribute__((swift_name("executeAsOneOrNull()")));
- (void)notifyDataChanged __attribute__((swift_name("notifyDataChanged()")));
- (void)removeListenerListener:(id<ETSKSqldelight_runtimeQueryListener>)listener __attribute__((swift_name("removeListener(listener:)")));
@property (readonly) id (^mapper)(id<ETSKSqldelight_runtimeSqlCursor>);
@end;

@interface ETSKSqldelight_runtimeQuery (Extensions)
- (id<ETSKKotlinx_coroutines_core_nativeReceiveChannel>)asChannel __attribute__((swift_name("asChannel()")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeCoroutineScope")))
@protocol ETSKKotlinx_coroutines_core_nativeCoroutineScope
@required
@property (readonly) id<ETSKKotlinCoroutineContext> coroutineContext;
@end;

__attribute__((swift_name("KotlinCoroutineContext")))
@protocol ETSKKotlinCoroutineContext
@required
- (id _Nullable)foldInitial:(id _Nullable)initial operation:(id _Nullable (^)(id _Nullable, id<ETSKKotlinCoroutineContextElement>))operation __attribute__((swift_name("fold(initial:operation:)")));
- (id<ETSKKotlinCoroutineContextElement> _Nullable)getKey:(id<ETSKKotlinCoroutineContextKey>)key __attribute__((swift_name("get(key:)")));
- (id<ETSKKotlinCoroutineContext>)minusKeyKey:(id<ETSKKotlinCoroutineContextKey>)key __attribute__((swift_name("minusKey(key:)")));
- (id<ETSKKotlinCoroutineContext>)plusContext:(id<ETSKKotlinCoroutineContext>)context __attribute__((swift_name("plus(context:)")));
@end;

__attribute__((swift_name("KotlinCoroutineContextElement")))
@protocol ETSKKotlinCoroutineContextElement <ETSKKotlinCoroutineContext>
@required
@property (readonly) id<ETSKKotlinCoroutineContextKey> key;
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeJob")))
@protocol ETSKKotlinx_coroutines_core_nativeJob <ETSKKotlinCoroutineContextElement>
@required
- (id<ETSKKotlinx_coroutines_core_nativeChildHandle>)attachChildChild:(id<ETSKKotlinx_coroutines_core_nativeChildJob>)child __attribute__((swift_name("attachChild(child:)")));
- (void)cancel __attribute__((swift_name("cancel()")));
- (BOOL)cancelCause:(ETSKKotlinThrowable * _Nullable)cause __attribute__((swift_name("cancel(cause:)")));
- (BOOL)cancel0 __attribute__((swift_name("cancel0()")));
- (ETSKKotlinx_coroutines_core_nativeCancellationException *)getCancellationException __attribute__((swift_name("getCancellationException()")));
- (id<ETSKKotlinx_coroutines_core_nativeDisposableHandle>)invokeOnCompletionOnCancelling:(BOOL)onCancelling invokeImmediately:(BOOL)invokeImmediately handler:(ETSKKotlinUnit *(^)(ETSKKotlinThrowable * _Nullable))handler __attribute__((swift_name("invokeOnCompletion(onCancelling:invokeImmediately:handler:)")));
- (id<ETSKKotlinx_coroutines_core_nativeDisposableHandle>)invokeOnCompletionHandler:(ETSKKotlinUnit *(^)(ETSKKotlinThrowable * _Nullable))handler __attribute__((swift_name("invokeOnCompletion(handler:)")));
- (id<ETSKKotlinx_coroutines_core_nativeJob>)plusOther:(id<ETSKKotlinx_coroutines_core_nativeJob>)other __attribute__((swift_name("plus(other:)")));
- (BOOL)start __attribute__((swift_name("start()")));
@property (readonly) id<ETSKKotlinSequence> children;
@property (readonly) BOOL isActive;
@property (readonly) BOOL isCancelled;
@property (readonly) BOOL isCompleted;
@property (readonly) id<ETSKKotlinx_coroutines_core_nativeSelectClause0> onJoin;
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeSendChannel")))
@protocol ETSKKotlinx_coroutines_core_nativeSendChannel
@required
- (BOOL)closeCause:(ETSKKotlinThrowable * _Nullable)cause __attribute__((swift_name("close(cause:)")));
- (void)invokeOnCloseHandler:(ETSKKotlinUnit *(^)(ETSKKotlinThrowable * _Nullable))handler __attribute__((swift_name("invokeOnClose(handler:)")));
- (BOOL)offerElement:(id _Nullable)element __attribute__((swift_name("offer(element:)")));
@property (readonly) BOOL isClosedForSend;
@property (readonly) BOOL isFull;
@property (readonly) id<ETSKKotlinx_coroutines_core_nativeSelectClause2> onSend;
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeReceiveChannel")))
@protocol ETSKKotlinx_coroutines_core_nativeReceiveChannel
@required
- (void)cancel __attribute__((swift_name("cancel()")));
- (BOOL)cancelCause:(ETSKKotlinThrowable * _Nullable)cause __attribute__((swift_name("cancel(cause:)")));
- (BOOL)cancel0 __attribute__((swift_name("cancel0()")));
- (id<ETSKKotlinx_coroutines_core_nativeChannelIterator>)iterator __attribute__((swift_name("iterator()")));
- (id _Nullable)poll __attribute__((swift_name("poll()")));
@property (readonly) BOOL isClosedForReceive;
@property (readonly) BOOL isEmpty;
@property (readonly) id<ETSKKotlinx_coroutines_core_nativeSelectClause1> onReceive;
@property (readonly) id<ETSKKotlinx_coroutines_core_nativeSelectClause1> onReceiveOrNull;
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeChannel")))
@protocol ETSKKotlinx_coroutines_core_nativeChannel <ETSKKotlinx_coroutines_core_nativeSendChannel, ETSKKotlinx_coroutines_core_nativeReceiveChannel>
@required
@end;

__attribute__((swift_name("Sqldelight_runtimeCloseable")))
@protocol ETSKSqldelight_runtimeCloseable
@required
- (void)close __attribute__((swift_name("close()")));
@end;

__attribute__((swift_name("Sqldelight_runtimeSqlDriver")))
@protocol ETSKSqldelight_runtimeSqlDriver <ETSKSqldelight_runtimeCloseable>
@required
- (ETSKSqldelight_runtimeTransacterTransaction * _Nullable)currentTransaction __attribute__((swift_name("currentTransaction()")));
- (void)executeIdentifier:(ETSKInt * _Nullable)identifier sql:(NSString *)sql parameters:(int32_t)parameters binders:(ETSKKotlinUnit *(^ _Nullable)(id<ETSKSqldelight_runtimeSqlPreparedStatement>))binders __attribute__((swift_name("execute(identifier:sql:parameters:binders:)")));
- (id<ETSKSqldelight_runtimeSqlCursor>)executeQueryIdentifier:(ETSKInt * _Nullable)identifier sql:(NSString *)sql parameters:(int32_t)parameters binders:(ETSKKotlinUnit *(^ _Nullable)(id<ETSKSqldelight_runtimeSqlPreparedStatement>))binders __attribute__((swift_name("executeQuery(identifier:sql:parameters:binders:)")));
- (ETSKSqldelight_runtimeTransacterTransaction *)doNewTransaction __attribute__((swift_name("doNewTransaction()")));
@end;

__attribute__((swift_name("KotlinAbstractCoroutineContextElement")))
@interface ETSKKotlinAbstractCoroutineContextElement : KotlinBase <ETSKKotlinCoroutineContextElement>
- (instancetype)initWithKey:(id<ETSKKotlinCoroutineContextKey>)key __attribute__((swift_name("init(key:)"))) __attribute__((objc_designated_initializer));
@end;

__attribute__((swift_name("KotlinContinuationInterceptor")))
@protocol ETSKKotlinContinuationInterceptor <ETSKKotlinCoroutineContextElement>
@required
- (id<ETSKKotlinContinuation>)interceptContinuationContinuation:(id<ETSKKotlinContinuation>)continuation __attribute__((swift_name("interceptContinuation(continuation:)")));
- (void)releaseInterceptedContinuationContinuation:(id<ETSKKotlinContinuation>)continuation __attribute__((swift_name("releaseInterceptedContinuation(continuation:)")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeCoroutineDispatcher")))
@interface ETSKKotlinx_coroutines_core_nativeCoroutineDispatcher : ETSKKotlinAbstractCoroutineContextElement <ETSKKotlinContinuationInterceptor>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithKey:(id<ETSKKotlinCoroutineContextKey>)key __attribute__((swift_name("init(key:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (void)dispatchContext:(id<ETSKKotlinCoroutineContext>)context block:(id<ETSKKotlinx_coroutines_core_nativeRunnable>)block __attribute__((swift_name("dispatch(context:block:)")));
- (void)dispatchYieldContext:(id<ETSKKotlinCoroutineContext>)context block:(id<ETSKKotlinx_coroutines_core_nativeRunnable>)block __attribute__((swift_name("dispatchYield(context:block:)")));
- (BOOL)isDispatchNeededContext:(id<ETSKKotlinCoroutineContext>)context __attribute__((swift_name("isDispatchNeeded(context:)")));
- (ETSKKotlinx_coroutines_core_nativeCoroutineDispatcher *)plusOther_:(ETSKKotlinx_coroutines_core_nativeCoroutineDispatcher *)other __attribute__((swift_name("plus(other_:)")));
@end;

__attribute__((swift_name("Sqldelight_runtimeColumnAdapter")))
@protocol ETSKSqldelight_runtimeColumnAdapter
@required
- (id)decodeDatabaseValue:(id _Nullable)databaseValue __attribute__((swift_name("decode(databaseValue:)")));
- (id _Nullable)encodeValue:(id)value __attribute__((swift_name("encode(value:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinUnit")))
@interface ETSKKotlinUnit : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)unit __attribute__((swift_name("init()")));
@end;

__attribute__((swift_name("Sqldelight_runtimeTransacter.Transaction")))
@interface ETSKSqldelight_runtimeTransacterTransaction : KotlinBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)afterCommitFunction:(ETSKKotlinUnit *(^)(void))function __attribute__((swift_name("afterCommit(function:)")));
- (void)afterRollbackFunction:(ETSKKotlinUnit *(^)(void))function __attribute__((swift_name("afterRollback(function:)")));
- (void)endTransactionSuccessful:(BOOL)successful __attribute__((swift_name("endTransaction(successful:)")));
- (void)rollback __attribute__((swift_name("rollback()")));
- (void)transactionBody:(ETSKKotlinUnit *(^)(ETSKSqldelight_runtimeTransacterTransaction *))body __attribute__((swift_name("transaction(body:)")));
@property (readonly) ETSKSqldelight_runtimeTransacterTransaction * _Nullable enclosingTransaction;
@end;

__attribute__((swift_name("Sqldelight_runtimeSqlCursor")))
@protocol ETSKSqldelight_runtimeSqlCursor <ETSKSqldelight_runtimeCloseable>
@required
- (ETSKKotlinByteArray * _Nullable)getBytesIndex:(int32_t)index __attribute__((swift_name("getBytes(index:)")));
- (ETSKDouble * _Nullable)getDoubleIndex:(int32_t)index __attribute__((swift_name("getDouble(index:)")));
- (ETSKLong * _Nullable)getLongIndex:(int32_t)index __attribute__((swift_name("getLong(index:)")));
- (NSString * _Nullable)getStringIndex:(int32_t)index __attribute__((swift_name("getString(index:)")));
- (BOOL)next __attribute__((swift_name("next()")));
@end;

__attribute__((swift_name("Sqldelight_runtimeQueryListener")))
@protocol ETSKSqldelight_runtimeQueryListener
@required
- (void)queryResultsChanged __attribute__((swift_name("queryResultsChanged()")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeDisposableHandle")))
@protocol ETSKKotlinx_coroutines_core_nativeDisposableHandle
@required
- (void)dispose __attribute__((swift_name("dispose()")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeChildHandle")))
@protocol ETSKKotlinx_coroutines_core_nativeChildHandle <ETSKKotlinx_coroutines_core_nativeDisposableHandle>
@required
- (BOOL)childCancelledCause:(ETSKKotlinThrowable *)cause __attribute__((swift_name("childCancelled(cause:)")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeChildJob")))
@protocol ETSKKotlinx_coroutines_core_nativeChildJob <ETSKKotlinx_coroutines_core_nativeJob>
@required
- (void)parentCancelledParentJob:(id<ETSKKotlinx_coroutines_core_nativeParentJob>)parentJob __attribute__((swift_name("parentCancelled(parentJob:)")));
@end;

__attribute__((swift_name("KotlinThrowable")))
@interface ETSKKotlinThrowable : KotlinBase
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(ETSKKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(ETSKKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (ETSKKotlinArray *)getStackTrace __attribute__((swift_name("getStackTrace()")));
- (void)printStackTrace __attribute__((swift_name("printStackTrace()")));
@property (readonly) ETSKKotlinThrowable * _Nullable cause;
@property (readonly) NSString * _Nullable message;
@end;

__attribute__((swift_name("KotlinException")))
@interface ETSKKotlinException : ETSKKotlinThrowable
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(ETSKKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(ETSKKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
@end;

__attribute__((swift_name("KotlinRuntimeException")))
@interface ETSKKotlinRuntimeException : ETSKKotlinException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(ETSKKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(ETSKKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
@end;

__attribute__((swift_name("KotlinIllegalStateException")))
@interface ETSKKotlinIllegalStateException : ETSKKotlinRuntimeException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(ETSKKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(ETSKKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeCancellationException")))
@interface ETSKKotlinx_coroutines_core_nativeCancellationException : ETSKKotlinIllegalStateException
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(ETSKKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (instancetype)initWithCause:(ETSKKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@end;

__attribute__((swift_name("KotlinSequence")))
@protocol ETSKKotlinSequence
@required
- (id<ETSKKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeSelectClause0")))
@protocol ETSKKotlinx_coroutines_core_nativeSelectClause0
@required
- (void)registerSelectClause0Select:(id<ETSKKotlinx_coroutines_core_nativeSelectInstance>)select block:(id<ETSKKotlinSuspendFunction0>)block __attribute__((swift_name("registerSelectClause0(select:block:)")));
@end;

__attribute__((swift_name("KotlinCoroutineContextKey")))
@protocol ETSKKotlinCoroutineContextKey
@required
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeSelectClause2")))
@protocol ETSKKotlinx_coroutines_core_nativeSelectClause2
@required
- (void)registerSelectClause2Select:(id<ETSKKotlinx_coroutines_core_nativeSelectInstance>)select param:(id _Nullable)param block:(id<ETSKKotlinSuspendFunction1>)block __attribute__((swift_name("registerSelectClause2(select:param:block:)")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeChannelIterator")))
@protocol ETSKKotlinx_coroutines_core_nativeChannelIterator
@required
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeSelectClause1")))
@protocol ETSKKotlinx_coroutines_core_nativeSelectClause1
@required
- (void)registerSelectClause1Select:(id<ETSKKotlinx_coroutines_core_nativeSelectInstance>)select block:(id<ETSKKotlinSuspendFunction1>)block __attribute__((swift_name("registerSelectClause1(select:block:)")));
@end;

__attribute__((swift_name("Sqldelight_runtimeSqlPreparedStatement")))
@protocol ETSKSqldelight_runtimeSqlPreparedStatement
@required
- (void)bindBytesIndex:(int32_t)index value:(ETSKKotlinByteArray * _Nullable)value __attribute__((swift_name("bindBytes(index:value:)")));
- (void)bindDoubleIndex:(int32_t)index value:(ETSKDouble * _Nullable)value __attribute__((swift_name("bindDouble(index:value:)")));
- (void)bindLongIndex:(int32_t)index value:(ETSKLong * _Nullable)value __attribute__((swift_name("bindLong(index:value:)")));
- (void)bindStringIndex:(int32_t)index value:(NSString * _Nullable)value __attribute__((swift_name("bindString(index:value:)")));
@end;

__attribute__((swift_name("KotlinContinuation")))
@protocol ETSKKotlinContinuation
@required
- (void)resumeWithResult:(id _Nullable)result __attribute__((swift_name("resumeWith(result:)")));
@property (readonly) id<ETSKKotlinCoroutineContext> context;
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeRunnable")))
@protocol ETSKKotlinx_coroutines_core_nativeRunnable
@required
- (void)run __attribute__((swift_name("run()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinByteArray")))
@interface ETSKKotlinByteArray : KotlinBase
+ (instancetype)arrayWithSize:(int32_t)size __attribute__((swift_name("init(size:)")));
+ (instancetype)arrayWithSize:(int32_t)size init:(ETSKByte *(^)(ETSKInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (int8_t)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (ETSKKotlinByteIterator *)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(int8_t)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size;
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeParentJob")))
@protocol ETSKKotlinx_coroutines_core_nativeParentJob <ETSKKotlinx_coroutines_core_nativeJob>
@required
- (ETSKKotlinThrowable *)getChildJobCancellationCause __attribute__((swift_name("getChildJobCancellationCause()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinArray")))
@interface ETSKKotlinArray : KotlinBase
+ (instancetype)arrayWithSize:(int32_t)size init:(id _Nullable (^)(ETSKInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (id _Nullable)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (id<ETSKKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(id _Nullable)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size;
@end;

__attribute__((swift_name("KotlinIterator")))
@protocol ETSKKotlinIterator
@required
- (BOOL)hasNext __attribute__((swift_name("hasNext()")));
- (id _Nullable)next_ __attribute__((swift_name("next_()")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeSelectInstance")))
@protocol ETSKKotlinx_coroutines_core_nativeSelectInstance
@required
- (void)disposeOnSelectHandle:(id<ETSKKotlinx_coroutines_core_nativeDisposableHandle>)handle __attribute__((swift_name("disposeOnSelect(handle:)")));
- (id _Nullable)performAtomicIfNotSelectedDesc:(ETSKKotlinx_coroutines_core_nativeAtomicDesc *)desc __attribute__((swift_name("performAtomicIfNotSelected(desc:)")));
- (id _Nullable)performAtomicTrySelectDesc:(ETSKKotlinx_coroutines_core_nativeAtomicDesc *)desc __attribute__((swift_name("performAtomicTrySelect(desc:)")));
- (void)resumeSelectCancellableWithExceptionException:(ETSKKotlinThrowable *)exception __attribute__((swift_name("resumeSelectCancellableWithException(exception:)")));
- (BOOL)trySelectIdempotent:(id _Nullable)idempotent __attribute__((swift_name("trySelect(idempotent:)")));
@property (readonly) id<ETSKKotlinContinuation> completion;
@property (readonly) BOOL isSelected;
@end;

__attribute__((swift_name("KotlinSuspendFunction")))
@protocol ETSKKotlinSuspendFunction
@required
@end;

__attribute__((swift_name("KotlinSuspendFunction0")))
@protocol ETSKKotlinSuspendFunction0 <ETSKKotlinSuspendFunction>
@required
@end;

__attribute__((swift_name("KotlinSuspendFunction1")))
@protocol ETSKKotlinSuspendFunction1 <ETSKKotlinSuspendFunction>
@required
@end;

__attribute__((swift_name("KotlinByteIterator")))
@interface ETSKKotlinByteIterator : KotlinBase <ETSKKotlinIterator>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (ETSKByte *)next_ __attribute__((swift_name("next_()")));
- (int8_t)nextByte __attribute__((swift_name("nextByte()")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeAtomicDesc")))
@interface ETSKKotlinx_coroutines_core_nativeAtomicDesc : KotlinBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)completeOp:(ETSKKotlinx_coroutines_core_nativeAtomicOp *)op failure:(id _Nullable)failure __attribute__((swift_name("complete(op:failure:)")));
- (id _Nullable)prepareOp:(ETSKKotlinx_coroutines_core_nativeAtomicOp *)op __attribute__((swift_name("prepare(op:)")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeOpDescriptor")))
@interface ETSKKotlinx_coroutines_core_nativeOpDescriptor : KotlinBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (id _Nullable)performAffected:(id _Nullable)affected __attribute__((swift_name("perform(affected:)")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeAtomicOp")))
@interface ETSKKotlinx_coroutines_core_nativeAtomicOp : ETSKKotlinx_coroutines_core_nativeOpDescriptor
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)completeAffected:(id _Nullable)affected failure:(id _Nullable)failure __attribute__((swift_name("complete(affected:failure:)")));
- (id _Nullable)prepareAffected:(id _Nullable)affected __attribute__((swift_name("prepare(affected:)")));
- (BOOL)tryDecideDecision:(id _Nullable)decision __attribute__((swift_name("tryDecide(decision:)")));
@property (readonly) BOOL isDecided;
@end;

NS_ASSUME_NONNULL_END

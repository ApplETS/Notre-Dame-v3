#import <Foundation/Foundation.h>

@class NotreDameDashboardViewModel, NotreDameViewModel, NotreDameDashboardCardsUseCase, NotreDameDashboardCardRepository, NotreDameDashboardCardQueries, NotreDameDashboardCard, NotreDameDb, NotreDameEtsMobileDb, NotreDameEtsMobileDispatchers, NotreDameKotlinx_coroutines_core_nativeCoroutineDispatcher, NotreDameActivite, NotreDameCours, NotreDameDashboardCardType, NotreDameKotlinEnum, NotreDameEnseignant, NotreDameEtudiant, NotreDameEvaluation, NotreDameEvaluationCours, NotreDameHoraireExamenFinal, NotreDameJourRemplace, NotreDameProgramme, NotreDameSeance, NotreDameSession, NotreDameSignetsUserCredentials, NotreDameUniversalCode, NotreDameSignetsUserCredentialsCompanion, NotreDameSommaireElementsEvaluation, NotreDameSommaireEtEvaluations, NotreDameUniversalCodeError, NotreDameDashboardCardEntityAdapter, NotreDameDashboardCardEntityImpl, NotreDameSqldelight_runtimeTransacter, NotreDameSqldelight_runtimeQuery, NotreDameKotlinUnit, NotreDameSqldelight_runtimeTransacterTransaction, NotreDameEtsMobileDbSchema, NotreDameKotlinNumber, NotreDameKotlinThrowable, NotreDameKotlinx_coroutines_core_nativeCancellationException, NotreDameKotlinAbstractCoroutineContextElement, NotreDameKotlinByteArray, NotreDameKotlinArray, NotreDameKotlinIllegalStateException, NotreDameKotlinRuntimeException, NotreDameKotlinException, NotreDameKotlinByteIterator, NotreDameKotlinx_coroutines_core_nativeAtomicDesc, NotreDameKotlinx_coroutines_core_nativeAtomicOp, NotreDameKotlinx_coroutines_core_nativeOpDescriptor;

@protocol NotreDameKotlinx_coroutines_core_nativeCoroutineScope, NotreDameKotlinx_coroutines_core_nativeJob, NotreDameKotlinx_coroutines_core_nativeChannel, NotreDameKotlinx_coroutines_core_nativeReceiveChannel, NotreDameSqldelight_runtimeSqlDriver, NotreDameAndroidParcel, NotreDameKotlinComparable, NotreDameDashboardCardEntity, NotreDameSqldelight_runtimeColumnAdapter, NotreDameSqldelight_runtimeSqlDriverSchema, NotreDameSqldelight_runtimeSqlCursor, NotreDameSqldelight_runtimeQueryListener, NotreDameKotlinCoroutineContext, NotreDameKotlinx_coroutines_core_nativeChildHandle, NotreDameKotlinx_coroutines_core_nativeChildJob, NotreDameKotlinx_coroutines_core_nativeDisposableHandle, NotreDameKotlinSequence, NotreDameKotlinx_coroutines_core_nativeSelectClause0, NotreDameKotlinCoroutineContextElement, NotreDameKotlinCoroutineContextKey, NotreDameKotlinx_coroutines_core_nativeSendChannel, NotreDameKotlinx_coroutines_core_nativeSelectClause2, NotreDameKotlinx_coroutines_core_nativeChannelIterator, NotreDameKotlinx_coroutines_core_nativeSelectClause1, NotreDameSqldelight_runtimeSqlPreparedStatement, NotreDameSqldelight_runtimeCloseable, NotreDameKotlinContinuationInterceptor, NotreDameKotlinContinuation, NotreDameKotlinx_coroutines_core_nativeRunnable, NotreDameKotlinx_coroutines_core_nativeParentJob, NotreDameKotlinIterator, NotreDameKotlinx_coroutines_core_nativeSelectInstance, NotreDameKotlinSuspendFunction0, NotreDameKotlinSuspendFunction1, NotreDameKotlinSuspendFunction;

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

__attribute__((swift_name("ViewModel")))
@interface NotreDameViewModel : KotlinBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (readonly) id<NotreDameKotlinx_coroutines_core_nativeCoroutineScope> scope;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DashboardViewModel")))
@interface NotreDameDashboardViewModel : NotreDameViewModel
- (instancetype)initWithDashboardCardsUseCase:(NotreDameDashboardCardsUseCase *)dashboardCardsUseCase __attribute__((swift_name("init(dashboardCardsUseCase:)"))) __attribute__((objc_designated_initializer));
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
- (id<NotreDameKotlinx_coroutines_core_nativeJob>)load __attribute__((swift_name("load()")));
- (void)onCardMovedFromPosition:(int32_t)fromPosition toPosition:(int32_t)toPosition __attribute__((swift_name("onCardMoved(fromPosition:toPosition:)")));
- (void)onCardRemovedPosition:(int32_t)position __attribute__((swift_name("onCardRemoved(position:)")));
- (void)undoLastRemove __attribute__((swift_name("undoLastRemove()")));
- (void)save __attribute__((swift_name("save()")));
- (void)restore __attribute__((swift_name("restore()")));
@property (readonly) id<NotreDameKotlinx_coroutines_core_nativeChannel> cardsChannel;
@property (readonly) id<NotreDameKotlinx_coroutines_core_nativeChannel> showUndoRemoveChannel;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DashboardCardRepository")))
@interface NotreDameDashboardCardRepository : KotlinBase
- (instancetype)initWithDashboardCardQueries:(NotreDameDashboardCardQueries *)dashboardCardQueries __attribute__((swift_name("init(dashboardCardQueries:)"))) __attribute__((objc_designated_initializer));
- (id<NotreDameKotlinx_coroutines_core_nativeReceiveChannel>)dashboardCards __attribute__((swift_name("dashboardCards()")));
- (id<NotreDameKotlinx_coroutines_core_nativeJob>)updateDashboardCardCard:(NotreDameDashboardCard *)card position:(int32_t)position __attribute__((swift_name("updateDashboardCard(card:position:)")));
- (void)restore __attribute__((swift_name("restore()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Db")))
@interface NotreDameDb : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)db __attribute__((swift_name("init()")));
- (void)setupDbDriver:(id<NotreDameSqldelight_runtimeSqlDriver>)driver __attribute__((swift_name("setupDb(driver:)")));
@property (readonly) BOOL ready;
@property (readonly) NotreDameEtsMobileDb *instance;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DashboardCardsUseCase")))
@interface NotreDameDashboardCardsUseCase : KotlinBase
- (instancetype)initWithRepository:(NotreDameDashboardCardRepository *)repository __attribute__((swift_name("init(repository:)"))) __attribute__((objc_designated_initializer));
- (id<NotreDameKotlinx_coroutines_core_nativeReceiveChannel>)fetch __attribute__((swift_name("fetch()")));
- (void)saveVisibleCards:(NSArray<NotreDameDashboardCard *> *)visibleCards hiddenCards:(NSArray<NotreDameDashboardCard *> *)hiddenCards __attribute__((swift_name("save(visibleCards:hiddenCards:)")));
- (void)restore __attribute__((swift_name("restore()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("EtsMobileDispatchers")))
@interface NotreDameEtsMobileDispatchers : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)etsMobileDispatchers __attribute__((swift_name("init()")));
@property (readonly) NotreDameKotlinx_coroutines_core_nativeCoroutineDispatcher *Default;
@property (readonly) NotreDameKotlinx_coroutines_core_nativeCoroutineDispatcher *IO;
@property (readonly) NotreDameKotlinx_coroutines_core_nativeCoroutineDispatcher *Main;
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
__attribute__((swift_name("DashboardCard")))
@interface NotreDameDashboardCard : KotlinBase
- (instancetype)initWithType:(NotreDameDashboardCardType *)type visible:(BOOL)visible dismissible:(BOOL)dismissible __attribute__((swift_name("init(type:visible:dismissible:)"))) __attribute__((objc_designated_initializer));
- (NotreDameDashboardCardType *)component1 __attribute__((swift_name("component1()")));
- (BOOL)component2 __attribute__((swift_name("component2()")));
- (BOOL)component3 __attribute__((swift_name("component3()")));
- (NotreDameDashboardCard *)doCopyType:(NotreDameDashboardCardType *)type visible:(BOOL)visible dismissible:(BOOL)dismissible __attribute__((swift_name("doCopy(type:visible:dismissible:)")));
@property (readonly) NotreDameDashboardCardType *type;
@property BOOL visible;
@property (readonly) BOOL dismissible;
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
__attribute__((swift_name("DashboardCard.Type")))
@interface NotreDameDashboardCardType : NotreDameKotlinEnum
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
@property (class, readonly) NotreDameDashboardCardType *dashboardCardApplets;
@property (class, readonly) NotreDameDashboardCardType *dashboardCardTodaySchedule;
@property (class, readonly) NotreDameDashboardCardType *dashboardCardGrades;
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (int32_t)compareToOther:(NotreDameDashboardCardType *)other __attribute__((swift_name("compareTo(other:)")));
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

__attribute__((swift_name("DashboardCardEntity")))
@protocol NotreDameDashboardCardEntity
@required
@property (readonly) NotreDameDashboardCardType *type;
@property (readonly) int64_t pos;
@property (readonly) BOOL visible;
@property (readonly) BOOL dismissible;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DashboardCardEntityAdapter")))
@interface NotreDameDashboardCardEntityAdapter : KotlinBase
- (instancetype)initWithTypeAdapter:(id<NotreDameSqldelight_runtimeColumnAdapter>)typeAdapter __attribute__((swift_name("init(typeAdapter:)"))) __attribute__((objc_designated_initializer));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DashboardCardEntityImpl")))
@interface NotreDameDashboardCardEntityImpl : KotlinBase <NotreDameDashboardCardEntity>
- (instancetype)initWithType:(NotreDameDashboardCardType *)type pos:(int64_t)pos visible:(BOOL)visible dismissible:(BOOL)dismissible __attribute__((swift_name("init(type:pos:visible:dismissible:)"))) __attribute__((objc_designated_initializer));
- (NotreDameDashboardCardType *)component1 __attribute__((swift_name("component1()")));
- (int64_t)component2 __attribute__((swift_name("component2()")));
- (BOOL)component3 __attribute__((swift_name("component3()")));
- (BOOL)component4 __attribute__((swift_name("component4()")));
- (NotreDameDashboardCardEntityImpl *)doCopyType:(NotreDameDashboardCardType *)type pos:(int64_t)pos visible:(BOOL)visible dismissible:(BOOL)dismissible __attribute__((swift_name("doCopy(type:pos:visible:dismissible:)")));
@end;

__attribute__((swift_name("Sqldelight_runtimeTransacter")))
@interface NotreDameSqldelight_runtimeTransacter : KotlinBase
- (instancetype)initWithDriver:(id<NotreDameSqldelight_runtimeSqlDriver>)driver __attribute__((swift_name("init(driver:)"))) __attribute__((objc_designated_initializer));
- (NSString *)createArgumentsCount:(int32_t)count offset:(int32_t)offset __attribute__((swift_name("createArguments(count:offset:)")));
- (void)notifyQueriesQueryList:(NSArray<NotreDameSqldelight_runtimeQuery *> *)queryList __attribute__((swift_name("notifyQueries(queryList:)")));
- (void)transactionNoEnclosing:(BOOL)noEnclosing body:(NotreDameKotlinUnit *(^)(NotreDameSqldelight_runtimeTransacterTransaction *))body __attribute__((swift_name("transaction(noEnclosing:body:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DashboardCardQueries")))
@interface NotreDameDashboardCardQueries : NotreDameSqldelight_runtimeTransacter
- (instancetype)initWithDatabase:(NotreDameEtsMobileDb *)database driver:(id<NotreDameSqldelight_runtimeSqlDriver>)driver __attribute__((swift_name("init(database:driver:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithDriver:(id<NotreDameSqldelight_runtimeSqlDriver>)driver __attribute__((swift_name("init(driver:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (NotreDameSqldelight_runtimeQuery *)selectAllMapper:(id (^)(NotreDameDashboardCardType *, NotreDameLong *, NotreDameBoolean *, NotreDameBoolean *))mapper __attribute__((swift_name("selectAll(mapper:)")));
- (NotreDameSqldelight_runtimeQuery *)selectAll __attribute__((swift_name("selectAll()")));
- (void)insertInitialCards __attribute__((swift_name("insertInitialCards()")));
- (void)updateCardPos:(int64_t)pos visible:(BOOL)visible type:(NotreDameDashboardCardType *)type __attribute__((swift_name("updateCard(pos:visible:type:)")));
- (void)deleteAll __attribute__((swift_name("deleteAll()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("EtsMobileDb")))
@interface NotreDameEtsMobileDb : NotreDameSqldelight_runtimeTransacter
- (instancetype)initWithDriver:(id<NotreDameSqldelight_runtimeSqlDriver>)driver dashboardCardEntityAdapter:(NotreDameDashboardCardEntityAdapter *)dashboardCardEntityAdapter __attribute__((swift_name("init(driver:dashboardCardEntityAdapter:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithDriver:(id<NotreDameSqldelight_runtimeSqlDriver>)driver __attribute__((swift_name("init(driver:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (readonly) NotreDameDashboardCardQueries *dashboardCardQueries;
@end;

__attribute__((swift_name("Sqldelight_runtimeSqlDriverSchema")))
@protocol NotreDameSqldelight_runtimeSqlDriverSchema
@required
- (void)createDriver:(id<NotreDameSqldelight_runtimeSqlDriver>)driver __attribute__((swift_name("create(driver:)")));
- (void)migrateDriver:(id<NotreDameSqldelight_runtimeSqlDriver>)driver oldVersion:(int32_t)oldVersion newVersion:(int32_t)newVersion __attribute__((swift_name("migrate(driver:oldVersion:newVersion:)")));
@property (readonly) int32_t version;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("EtsMobileDb.Schema")))
@interface NotreDameEtsMobileDbSchema : KotlinBase <NotreDameSqldelight_runtimeSqlDriverSchema>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)schema __attribute__((swift_name("init()")));
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

__attribute__((swift_name("Sqldelight_runtimeQuery")))
@interface NotreDameSqldelight_runtimeQuery : KotlinBase
- (instancetype)initWithQueries:(NSMutableArray<NotreDameSqldelight_runtimeQuery *> *)queries mapper:(id (^)(id<NotreDameSqldelight_runtimeSqlCursor>))mapper __attribute__((swift_name("init(queries:mapper:)"))) __attribute__((objc_designated_initializer));
- (void)addListenerListener:(id<NotreDameSqldelight_runtimeQueryListener>)listener __attribute__((swift_name("addListener(listener:)")));
- (id<NotreDameSqldelight_runtimeSqlCursor>)execute __attribute__((swift_name("execute()")));
- (NSArray<id> *)executeAsList __attribute__((swift_name("executeAsList()")));
- (id)executeAsOne __attribute__((swift_name("executeAsOne()")));
- (id _Nullable)executeAsOneOrNull __attribute__((swift_name("executeAsOneOrNull()")));
- (void)notifyDataChanged __attribute__((swift_name("notifyDataChanged()")));
- (void)removeListenerListener:(id<NotreDameSqldelight_runtimeQueryListener>)listener __attribute__((swift_name("removeListener(listener:)")));
@property (readonly) id (^mapper)(id<NotreDameSqldelight_runtimeSqlCursor>);
@end;

@interface NotreDameSqldelight_runtimeQuery (Extensions)
- (id<NotreDameKotlinx_coroutines_core_nativeReceiveChannel>)asChannel __attribute__((swift_name("asChannel()")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeCoroutineScope")))
@protocol NotreDameKotlinx_coroutines_core_nativeCoroutineScope
@required
@property (readonly) id<NotreDameKotlinCoroutineContext> coroutineContext;
@end;

__attribute__((swift_name("KotlinCoroutineContext")))
@protocol NotreDameKotlinCoroutineContext
@required
- (id _Nullable)foldInitial:(id _Nullable)initial operation:(id _Nullable (^)(id _Nullable, id<NotreDameKotlinCoroutineContextElement>))operation __attribute__((swift_name("fold(initial:operation:)")));
- (id<NotreDameKotlinCoroutineContextElement> _Nullable)getKey:(id<NotreDameKotlinCoroutineContextKey>)key __attribute__((swift_name("get(key:)")));
- (id<NotreDameKotlinCoroutineContext>)minusKeyKey:(id<NotreDameKotlinCoroutineContextKey>)key __attribute__((swift_name("minusKey(key:)")));
- (id<NotreDameKotlinCoroutineContext>)plusContext:(id<NotreDameKotlinCoroutineContext>)context __attribute__((swift_name("plus(context:)")));
@end;

__attribute__((swift_name("KotlinCoroutineContextElement")))
@protocol NotreDameKotlinCoroutineContextElement <NotreDameKotlinCoroutineContext>
@required
@property (readonly) id<NotreDameKotlinCoroutineContextKey> key;
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeJob")))
@protocol NotreDameKotlinx_coroutines_core_nativeJob <NotreDameKotlinCoroutineContextElement>
@required
- (id<NotreDameKotlinx_coroutines_core_nativeChildHandle>)attachChildChild:(id<NotreDameKotlinx_coroutines_core_nativeChildJob>)child __attribute__((swift_name("attachChild(child:)")));
- (void)cancel __attribute__((swift_name("cancel()")));
- (BOOL)cancelCause:(NotreDameKotlinThrowable * _Nullable)cause __attribute__((swift_name("cancel(cause:)")));
- (BOOL)cancel0 __attribute__((swift_name("cancel0()")));
- (NotreDameKotlinx_coroutines_core_nativeCancellationException *)getCancellationException __attribute__((swift_name("getCancellationException()")));
- (id<NotreDameKotlinx_coroutines_core_nativeDisposableHandle>)invokeOnCompletionOnCancelling:(BOOL)onCancelling invokeImmediately:(BOOL)invokeImmediately handler:(NotreDameKotlinUnit *(^)(NotreDameKotlinThrowable * _Nullable))handler __attribute__((swift_name("invokeOnCompletion(onCancelling:invokeImmediately:handler:)")));
- (id<NotreDameKotlinx_coroutines_core_nativeDisposableHandle>)invokeOnCompletionHandler:(NotreDameKotlinUnit *(^)(NotreDameKotlinThrowable * _Nullable))handler __attribute__((swift_name("invokeOnCompletion(handler:)")));
- (id<NotreDameKotlinx_coroutines_core_nativeJob>)plusOther:(id<NotreDameKotlinx_coroutines_core_nativeJob>)other __attribute__((swift_name("plus(other:)")));
- (BOOL)start __attribute__((swift_name("start()")));
@property (readonly) id<NotreDameKotlinSequence> children;
@property (readonly) BOOL isActive;
@property (readonly) BOOL isCancelled;
@property (readonly) BOOL isCompleted;
@property (readonly) id<NotreDameKotlinx_coroutines_core_nativeSelectClause0> onJoin;
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeSendChannel")))
@protocol NotreDameKotlinx_coroutines_core_nativeSendChannel
@required
- (BOOL)closeCause:(NotreDameKotlinThrowable * _Nullable)cause __attribute__((swift_name("close(cause:)")));
- (void)invokeOnCloseHandler:(NotreDameKotlinUnit *(^)(NotreDameKotlinThrowable * _Nullable))handler __attribute__((swift_name("invokeOnClose(handler:)")));
- (BOOL)offerElement:(id _Nullable)element __attribute__((swift_name("offer(element:)")));
@property (readonly) BOOL isClosedForSend;
@property (readonly) BOOL isFull;
@property (readonly) id<NotreDameKotlinx_coroutines_core_nativeSelectClause2> onSend;
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeReceiveChannel")))
@protocol NotreDameKotlinx_coroutines_core_nativeReceiveChannel
@required
- (void)cancel __attribute__((swift_name("cancel()")));
- (BOOL)cancelCause:(NotreDameKotlinThrowable * _Nullable)cause __attribute__((swift_name("cancel(cause:)")));
- (BOOL)cancel0 __attribute__((swift_name("cancel0()")));
- (id<NotreDameKotlinx_coroutines_core_nativeChannelIterator>)iterator __attribute__((swift_name("iterator()")));
- (id _Nullable)poll __attribute__((swift_name("poll()")));
@property (readonly) BOOL isClosedForReceive;
@property (readonly) BOOL isEmpty;
@property (readonly) id<NotreDameKotlinx_coroutines_core_nativeSelectClause1> onReceive;
@property (readonly) id<NotreDameKotlinx_coroutines_core_nativeSelectClause1> onReceiveOrNull;
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeChannel")))
@protocol NotreDameKotlinx_coroutines_core_nativeChannel <NotreDameKotlinx_coroutines_core_nativeSendChannel, NotreDameKotlinx_coroutines_core_nativeReceiveChannel>
@required
@end;

__attribute__((swift_name("Sqldelight_runtimeCloseable")))
@protocol NotreDameSqldelight_runtimeCloseable
@required
- (void)close __attribute__((swift_name("close()")));
@end;

__attribute__((swift_name("Sqldelight_runtimeSqlDriver")))
@protocol NotreDameSqldelight_runtimeSqlDriver <NotreDameSqldelight_runtimeCloseable>
@required
- (NotreDameSqldelight_runtimeTransacterTransaction * _Nullable)currentTransaction __attribute__((swift_name("currentTransaction()")));
- (void)executeIdentifier:(NotreDameInt * _Nullable)identifier sql:(NSString *)sql parameters:(int32_t)parameters binders:(NotreDameKotlinUnit *(^ _Nullable)(id<NotreDameSqldelight_runtimeSqlPreparedStatement>))binders __attribute__((swift_name("execute(identifier:sql:parameters:binders:)")));
- (id<NotreDameSqldelight_runtimeSqlCursor>)executeQueryIdentifier:(NotreDameInt * _Nullable)identifier sql:(NSString *)sql parameters:(int32_t)parameters binders:(NotreDameKotlinUnit *(^ _Nullable)(id<NotreDameSqldelight_runtimeSqlPreparedStatement>))binders __attribute__((swift_name("executeQuery(identifier:sql:parameters:binders:)")));
- (NotreDameSqldelight_runtimeTransacterTransaction *)doNewTransaction __attribute__((swift_name("doNewTransaction()")));
@end;

__attribute__((swift_name("KotlinAbstractCoroutineContextElement")))
@interface NotreDameKotlinAbstractCoroutineContextElement : KotlinBase <NotreDameKotlinCoroutineContextElement>
- (instancetype)initWithKey:(id<NotreDameKotlinCoroutineContextKey>)key __attribute__((swift_name("init(key:)"))) __attribute__((objc_designated_initializer));
@end;

__attribute__((swift_name("KotlinContinuationInterceptor")))
@protocol NotreDameKotlinContinuationInterceptor <NotreDameKotlinCoroutineContextElement>
@required
- (id<NotreDameKotlinContinuation>)interceptContinuationContinuation:(id<NotreDameKotlinContinuation>)continuation __attribute__((swift_name("interceptContinuation(continuation:)")));
- (void)releaseInterceptedContinuationContinuation:(id<NotreDameKotlinContinuation>)continuation __attribute__((swift_name("releaseInterceptedContinuation(continuation:)")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeCoroutineDispatcher")))
@interface NotreDameKotlinx_coroutines_core_nativeCoroutineDispatcher : NotreDameKotlinAbstractCoroutineContextElement <NotreDameKotlinContinuationInterceptor>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithKey:(id<NotreDameKotlinCoroutineContextKey>)key __attribute__((swift_name("init(key:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (void)dispatchContext:(id<NotreDameKotlinCoroutineContext>)context block:(id<NotreDameKotlinx_coroutines_core_nativeRunnable>)block __attribute__((swift_name("dispatch(context:block:)")));
- (void)dispatchYieldContext:(id<NotreDameKotlinCoroutineContext>)context block:(id<NotreDameKotlinx_coroutines_core_nativeRunnable>)block __attribute__((swift_name("dispatchYield(context:block:)")));
- (BOOL)isDispatchNeededContext:(id<NotreDameKotlinCoroutineContext>)context __attribute__((swift_name("isDispatchNeeded(context:)")));
- (NotreDameKotlinx_coroutines_core_nativeCoroutineDispatcher *)plusOther_:(NotreDameKotlinx_coroutines_core_nativeCoroutineDispatcher *)other __attribute__((swift_name("plus(other_:)")));
@end;

__attribute__((swift_name("Sqldelight_runtimeColumnAdapter")))
@protocol NotreDameSqldelight_runtimeColumnAdapter
@required
- (id)decodeDatabaseValue:(id _Nullable)databaseValue __attribute__((swift_name("decode(databaseValue:)")));
- (id _Nullable)encodeValue:(id)value __attribute__((swift_name("encode(value:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinUnit")))
@interface NotreDameKotlinUnit : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)unit __attribute__((swift_name("init()")));
@end;

__attribute__((swift_name("Sqldelight_runtimeTransacter.Transaction")))
@interface NotreDameSqldelight_runtimeTransacterTransaction : KotlinBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)afterCommitFunction:(NotreDameKotlinUnit *(^)(void))function __attribute__((swift_name("afterCommit(function:)")));
- (void)afterRollbackFunction:(NotreDameKotlinUnit *(^)(void))function __attribute__((swift_name("afterRollback(function:)")));
- (void)endTransactionSuccessful:(BOOL)successful __attribute__((swift_name("endTransaction(successful:)")));
- (void)rollback __attribute__((swift_name("rollback()")));
- (void)transactionBody:(NotreDameKotlinUnit *(^)(NotreDameSqldelight_runtimeTransacterTransaction *))body __attribute__((swift_name("transaction(body:)")));
@property (readonly) NotreDameSqldelight_runtimeTransacterTransaction * _Nullable enclosingTransaction;
@end;

__attribute__((swift_name("Sqldelight_runtimeSqlCursor")))
@protocol NotreDameSqldelight_runtimeSqlCursor <NotreDameSqldelight_runtimeCloseable>
@required
- (NotreDameKotlinByteArray * _Nullable)getBytesIndex:(int32_t)index __attribute__((swift_name("getBytes(index:)")));
- (NotreDameDouble * _Nullable)getDoubleIndex:(int32_t)index __attribute__((swift_name("getDouble(index:)")));
- (NotreDameLong * _Nullable)getLongIndex:(int32_t)index __attribute__((swift_name("getLong(index:)")));
- (NSString * _Nullable)getStringIndex:(int32_t)index __attribute__((swift_name("getString(index:)")));
- (BOOL)next __attribute__((swift_name("next()")));
@end;

__attribute__((swift_name("Sqldelight_runtimeQueryListener")))
@protocol NotreDameSqldelight_runtimeQueryListener
@required
- (void)queryResultsChanged __attribute__((swift_name("queryResultsChanged()")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeDisposableHandle")))
@protocol NotreDameKotlinx_coroutines_core_nativeDisposableHandle
@required
- (void)dispose __attribute__((swift_name("dispose()")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeChildHandle")))
@protocol NotreDameKotlinx_coroutines_core_nativeChildHandle <NotreDameKotlinx_coroutines_core_nativeDisposableHandle>
@required
- (BOOL)childCancelledCause:(NotreDameKotlinThrowable *)cause __attribute__((swift_name("childCancelled(cause:)")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeChildJob")))
@protocol NotreDameKotlinx_coroutines_core_nativeChildJob <NotreDameKotlinx_coroutines_core_nativeJob>
@required
- (void)parentCancelledParentJob:(id<NotreDameKotlinx_coroutines_core_nativeParentJob>)parentJob __attribute__((swift_name("parentCancelled(parentJob:)")));
@end;

__attribute__((swift_name("KotlinThrowable")))
@interface NotreDameKotlinThrowable : KotlinBase
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(NotreDameKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(NotreDameKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (NotreDameKotlinArray *)getStackTrace __attribute__((swift_name("getStackTrace()")));
- (void)printStackTrace __attribute__((swift_name("printStackTrace()")));
@property (readonly) NotreDameKotlinThrowable * _Nullable cause;
@property (readonly) NSString * _Nullable message;
@end;

__attribute__((swift_name("KotlinException")))
@interface NotreDameKotlinException : NotreDameKotlinThrowable
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(NotreDameKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(NotreDameKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
@end;

__attribute__((swift_name("KotlinRuntimeException")))
@interface NotreDameKotlinRuntimeException : NotreDameKotlinException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(NotreDameKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(NotreDameKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
@end;

__attribute__((swift_name("KotlinIllegalStateException")))
@interface NotreDameKotlinIllegalStateException : NotreDameKotlinRuntimeException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(NotreDameKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(NotreDameKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeCancellationException")))
@interface NotreDameKotlinx_coroutines_core_nativeCancellationException : NotreDameKotlinIllegalStateException
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(NotreDameKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (instancetype)initWithCause:(NotreDameKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@end;

__attribute__((swift_name("KotlinSequence")))
@protocol NotreDameKotlinSequence
@required
- (id<NotreDameKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeSelectClause0")))
@protocol NotreDameKotlinx_coroutines_core_nativeSelectClause0
@required
- (void)registerSelectClause0Select:(id<NotreDameKotlinx_coroutines_core_nativeSelectInstance>)select block:(id<NotreDameKotlinSuspendFunction0>)block __attribute__((swift_name("registerSelectClause0(select:block:)")));
@end;

__attribute__((swift_name("KotlinCoroutineContextKey")))
@protocol NotreDameKotlinCoroutineContextKey
@required
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeSelectClause2")))
@protocol NotreDameKotlinx_coroutines_core_nativeSelectClause2
@required
- (void)registerSelectClause2Select:(id<NotreDameKotlinx_coroutines_core_nativeSelectInstance>)select param:(id _Nullable)param block:(id<NotreDameKotlinSuspendFunction1>)block __attribute__((swift_name("registerSelectClause2(select:param:block:)")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeChannelIterator")))
@protocol NotreDameKotlinx_coroutines_core_nativeChannelIterator
@required
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeSelectClause1")))
@protocol NotreDameKotlinx_coroutines_core_nativeSelectClause1
@required
- (void)registerSelectClause1Select:(id<NotreDameKotlinx_coroutines_core_nativeSelectInstance>)select block:(id<NotreDameKotlinSuspendFunction1>)block __attribute__((swift_name("registerSelectClause1(select:block:)")));
@end;

__attribute__((swift_name("Sqldelight_runtimeSqlPreparedStatement")))
@protocol NotreDameSqldelight_runtimeSqlPreparedStatement
@required
- (void)bindBytesIndex:(int32_t)index value:(NotreDameKotlinByteArray * _Nullable)value __attribute__((swift_name("bindBytes(index:value:)")));
- (void)bindDoubleIndex:(int32_t)index value:(NotreDameDouble * _Nullable)value __attribute__((swift_name("bindDouble(index:value:)")));
- (void)bindLongIndex:(int32_t)index value:(NotreDameLong * _Nullable)value __attribute__((swift_name("bindLong(index:value:)")));
- (void)bindStringIndex:(int32_t)index value:(NSString * _Nullable)value __attribute__((swift_name("bindString(index:value:)")));
@end;

__attribute__((swift_name("KotlinContinuation")))
@protocol NotreDameKotlinContinuation
@required
- (void)resumeWithResult:(id _Nullable)result __attribute__((swift_name("resumeWith(result:)")));
@property (readonly) id<NotreDameKotlinCoroutineContext> context;
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeRunnable")))
@protocol NotreDameKotlinx_coroutines_core_nativeRunnable
@required
- (void)run __attribute__((swift_name("run()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinByteArray")))
@interface NotreDameKotlinByteArray : KotlinBase
+ (instancetype)arrayWithSize:(int32_t)size __attribute__((swift_name("init(size:)")));
+ (instancetype)arrayWithSize:(int32_t)size init:(NotreDameByte *(^)(NotreDameInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (int8_t)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (NotreDameKotlinByteIterator *)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(int8_t)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size;
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeParentJob")))
@protocol NotreDameKotlinx_coroutines_core_nativeParentJob <NotreDameKotlinx_coroutines_core_nativeJob>
@required
- (NotreDameKotlinThrowable *)getChildJobCancellationCause __attribute__((swift_name("getChildJobCancellationCause()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinArray")))
@interface NotreDameKotlinArray : KotlinBase
+ (instancetype)arrayWithSize:(int32_t)size init:(id _Nullable (^)(NotreDameInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (id _Nullable)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (id<NotreDameKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(id _Nullable)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size;
@end;

__attribute__((swift_name("KotlinIterator")))
@protocol NotreDameKotlinIterator
@required
- (BOOL)hasNext __attribute__((swift_name("hasNext()")));
- (id _Nullable)next_ __attribute__((swift_name("next_()")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeSelectInstance")))
@protocol NotreDameKotlinx_coroutines_core_nativeSelectInstance
@required
- (void)disposeOnSelectHandle:(id<NotreDameKotlinx_coroutines_core_nativeDisposableHandle>)handle __attribute__((swift_name("disposeOnSelect(handle:)")));
- (id _Nullable)performAtomicIfNotSelectedDesc:(NotreDameKotlinx_coroutines_core_nativeAtomicDesc *)desc __attribute__((swift_name("performAtomicIfNotSelected(desc:)")));
- (id _Nullable)performAtomicTrySelectDesc:(NotreDameKotlinx_coroutines_core_nativeAtomicDesc *)desc __attribute__((swift_name("performAtomicTrySelect(desc:)")));
- (void)resumeSelectCancellableWithExceptionException:(NotreDameKotlinThrowable *)exception __attribute__((swift_name("resumeSelectCancellableWithException(exception:)")));
- (BOOL)trySelectIdempotent:(id _Nullable)idempotent __attribute__((swift_name("trySelect(idempotent:)")));
@property (readonly) id<NotreDameKotlinContinuation> completion;
@property (readonly) BOOL isSelected;
@end;

__attribute__((swift_name("KotlinSuspendFunction")))
@protocol NotreDameKotlinSuspendFunction
@required
@end;

__attribute__((swift_name("KotlinSuspendFunction0")))
@protocol NotreDameKotlinSuspendFunction0 <NotreDameKotlinSuspendFunction>
@required
@end;

__attribute__((swift_name("KotlinSuspendFunction1")))
@protocol NotreDameKotlinSuspendFunction1 <NotreDameKotlinSuspendFunction>
@required
@end;

__attribute__((swift_name("KotlinByteIterator")))
@interface NotreDameKotlinByteIterator : KotlinBase <NotreDameKotlinIterator>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (NotreDameByte *)next_ __attribute__((swift_name("next_()")));
- (int8_t)nextByte __attribute__((swift_name("nextByte()")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeAtomicDesc")))
@interface NotreDameKotlinx_coroutines_core_nativeAtomicDesc : KotlinBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)completeOp:(NotreDameKotlinx_coroutines_core_nativeAtomicOp *)op failure:(id _Nullable)failure __attribute__((swift_name("complete(op:failure:)")));
- (id _Nullable)prepareOp:(NotreDameKotlinx_coroutines_core_nativeAtomicOp *)op __attribute__((swift_name("prepare(op:)")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeOpDescriptor")))
@interface NotreDameKotlinx_coroutines_core_nativeOpDescriptor : KotlinBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (id _Nullable)performAffected:(id _Nullable)affected __attribute__((swift_name("perform(affected:)")));
@end;

__attribute__((swift_name("Kotlinx_coroutines_core_nativeAtomicOp")))
@interface NotreDameKotlinx_coroutines_core_nativeAtomicOp : NotreDameKotlinx_coroutines_core_nativeOpDescriptor
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)completeAffected:(id _Nullable)affected failure:(id _Nullable)failure __attribute__((swift_name("complete(affected:failure:)")));
- (id _Nullable)prepareAffected:(id _Nullable)affected __attribute__((swift_name("prepare(affected:)")));
- (BOOL)tryDecideDecision:(id _Nullable)decision __attribute__((swift_name("tryDecide(decision:)")));
@property (readonly) BOOL isDecided;
@end;

NS_ASSUME_NONNULL_END

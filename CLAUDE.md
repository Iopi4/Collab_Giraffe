# Android Compose Development Agent for Claude Code

<semantic_engineering_role version="5.0" target="claude-code" platform="android-compose-kotlin">
  <core_optimization>
    Contract-first mobile development with Nova backend integration.
    Every line of code must be testable, composable, and reactive.
    UI and business logic strictly separated via clean architecture.
  </core_optimization>

  <android_compose_context>
    <kotlin_first>
      <idioms>Use Kotlin idioms: data classes, sealed classes, extension functions</idioms>
      <coroutines>Structured concurrency with proper scope management</coroutines>
      <null_safety>Leverage type system for compile-time safety</null_safety>
    </kotlin_first>
    
    <compose_patterns>
      <state_management>
        ```kotlin
        // State hoisting pattern
        @Composable
        fun StatefulScreen() {
            var uiState by remember { mutableStateOf(ScreenState()) }
            StatelessContent(
                state = uiState,
                onAction = { action -> uiState = reduceAction(action) }
            )
        }
        ```
      </state_management>
      
      <recomposition_optimization>
        - Use `remember` for expensive operations
        - `derivedStateOf` for computed values
        - `key()` for list items stability
        - Immutable data classes for state
      </recomposition_optimization>
      
      <navigation>
        ```kotlin
        // Type-safe navigation with contracts
        @Serializable
        data class UserDetail(val userId: String)
        
        NavHost(navController) {
            composable<UserDetail> { backStackEntry ->
                val args = backStackEntry.toRoute<UserDetail>()
                UserDetailScreen(args.userId)
            }
        }
        ```
      </navigation>
    </compose_patterns>
  </android_compose_context>

  <contract_driven_development>
    <api_contracts>
      <location>contracts/api/</location>
      <format>
        ```kotlin
        // contracts/api/UserContract.kt
        @Serializable
        data class UserContract(
            val endpoint: String = "/api/users",
            val request: UserRequest,
            val response: UserResponse,
            val errors: List<ErrorCase>
        )
        
        @Serializable
        data class UserRequest(
            val email: String,
            val password: String
        ) {
            fun validate(): ValidationResult = 
                ValidationResult.build {
                    require(email.contains("@")) { "Invalid email" }
                    require(password.length >= 8) { "Password too short" }
                }
        }
        ```
      </format>
      
      <sync_protocol>
        Nova backend reads same contract files
        Mobile generates Retrofit interfaces from contracts
        Shared validation logic between platforms
      </sync_protocol>
    </api_contracts>

    <ui_contracts>
      ```kotlin
      // contracts/ui/ScreenContract.kt
      sealed interface ScreenContract<State, Action, Effect> {
          val initialState: State
          fun reduce(state: State, action: Action): State
          suspend fun handleEffect(effect: Effect)
      }
      
      // Implementation
      class LoginScreenContract : ScreenContract<LoginState, LoginAction, LoginEffect> {
          override val initialState = LoginState()
          
          override fun reduce(state: LoginState, action: LoginAction) = when(action) {
              is LoginAction.EmailChanged -> state.copy(email = action.email)
              is LoginAction.Submit -> state.copy(isLoading = true)
          }
      }
      ```
    </ui_contracts>

    <data_contracts>
      ```kotlin
      // contracts/data/CacheContract.kt
      interface CacheContract<T> {
          suspend fun get(key: String): T?
          suspend fun set(key: String, value: T, ttl: Duration = 5.minutes)
          suspend fun invalidate(pattern: String = "*")
      }
      ```
    </data_contracts>
  </contract_driven_development>

  <claude_code_integration>
    <file_operations>
      ```bash
      # Claude Code file structure awareness
      project/
      ├── app/src/main/java/com/app/
      │   ├── ui/          # Compose screens
      │   ├── domain/      # Business logic
      │   ├── data/        # Repositories
      │   └── contracts/   # Shared with Nova
      ├── buildSrc/        # Convention plugins
      └── gradle/libs.versions.toml
      ```
    </file_operations>

    <incremental_development>
      <pattern>
        1. Create contract file first
        2. Generate test from contract
        3. Implement with TDD
        4. Validate against Nova backend
      </pattern>
      
      <commands>
        ```bash
        # Generate boilerplate from contract
        ./gradlew generateFromContract --contract=UserContract
        
        # Run contract validation
        ./gradlew validateContracts --backend=http://nova.local
        
        # Hot reload with contract watching
        ./gradlew composeHotReload --watch-contracts
        ```
      </commands>
    </incremental_development>

    <context_awareness>
      Track current: screen, viewmodel, repository, contract
      Infer from: recent edits, test failures, build errors
      Suggest: next logical implementation step
    </context_awareness>
  </claude_code_integration>

  <architecture_layers>
    <presentation>
      ```kotlin
      // UI Layer - Pure Compose
      @Composable
      fun UserListScreen(
          viewModel: UserListViewModel = hiltViewModel()
      ) {
          val uiState by viewModel.uiState.collectAsStateWithLifecycle()
          
          UserListContent(
              state = uiState,
              onUserClick = viewModel::selectUser,
              onRefresh = viewModel::refresh
          )
      }
      
      // ViewModel - State management
      @HiltViewModel
      class UserListViewModel @Inject constructor(
          private val userRepository: UserRepository,
          private val analyticsTracker: AnalyticsTracker
      ) : ViewModel() {
          private val _uiState = MutableStateFlow(UserListState())
          val uiState = _uiState.asStateFlow()
          
          init {
              loadUsers()
          }
          
          private fun loadUsers() = viewModelScope.launch {
              userRepository.getUsers()
                  .flowOn(Dispatchers.IO)
                  .catch { _uiState.update { it.copy(error = it) } }
                  .collect { users -> 
                      _uiState.update { it.copy(users = users, isLoading = false) }
                  }
          }
      }
      ```
    </presentation>

    <domain>
      ```kotlin
      // Use Case - Business logic
      class ValidateLoginUseCase @Inject constructor(
          private val userRepository: UserRepository
      ) {
          suspend operator fun invoke(email: String, password: String): Result<User> {
              // Business rules from contract
              val validation = UserRequest(email, password).validate()
              if (!validation.isValid) {
                  return Result.failure(ValidationException(validation.errors))
              }
              
              return userRepository.login(email, password)
          }
      }
      ```
    </domain>

    <data>
      ```kotlin
      // Repository - Data coordination
      class UserRepositoryImpl @Inject constructor(
          private val api: NovaApiService,
          private val cache: UserCache,
          private val db: UserDao
      ) : UserRepository {
          override fun getUsers(): Flow<List<User>> = flow {
              // Emit cached data first
              cache.getUsers()?.let { emit(it) }
              
              // Fetch fresh data
              val users = api.getUsers()
              cache.setUsers(users)
              db.insertUsers(users)
              
              emit(users)
          }.catch { error ->
              // Fallback to database
              emit(db.getAllUsers())
              throw error
          }
      }
      ```
    </data>

    <infrastructure>
      ```kotlin
      // Retrofit service from contract
      interface NovaApiService {
          @POST("/api/auth/login")
          suspend fun login(@Body request: UserRequest): UserResponse
          
          companion object {
              fun create(contractValidator: ContractValidator): NovaApiService {
                  return Retrofit.Builder()
                      .baseUrl(BuildConfig.NOVA_BASE_URL)
                      .addConverterFactory(ContractAwareConverter(contractValidator))
                      .build()
                      .create()
              }
          }
      }
      ```
    </infrastructure>
  </architecture_layers>

  <testing_strategy>
    <contract_tests>
      ```kotlin
      // Test contract compliance
      @Test
      fun `API response matches contract`() = runTest {
          val contract = UserContract()
          val response = api.getUser("123")
          
          assertThat(response).conformsTo(contract.response)
      }
      ```
    </contract_tests>

    <compose_tests>
      ```kotlin
      @Test
      fun `UserList displays items correctly`() {
          composeTestRule.setContent {
              UserListScreen(
                  state = UserListState(users = testUsers),
                  onUserClick = {}
              )
          }
          
          composeTestRule
              .onNodeWithTag("user_list")
              .assertIsDisplayed()
              .onChildren()
              .assertCountEquals(testUsers.size)
      }
      ```
    </compose_tests>

    <integration_tests>
      ```kotlin
      @Test
      fun `Login flow works end-to-end`() = runTest {
          // Mock Nova backend with contract
          mockWebServer.enqueue(
              MockResponse()
                  .setBody(contract.response.toJson())
                  .setResponseCode(200)
          )
          
          val result = loginUseCase("test@test.com", "password")
          
          assertThat(result.isSuccess).isTrue()
          assertThat(mockWebServer.takeRequest().path).isEqualTo(contract.endpoint)
      }
      ```
    </integration_tests>
  </testing_strategy>

  <performance_optimization>
    <compose_performance>
      ```kotlin
      // Baseline profile for startup optimization
      @ExperimentalBaselineProfilesApi
      @RunWith(AndroidJUnit4::class)
      class BaselineProfileGenerator {
          @get:Rule
          val rule = BaselineProfileRule()
          
          @Test
          fun generate() = rule.collect(
              packageName = "com.app",
              maxIterations = 10,
              stableIterations = 5,
              includeInStartupProfile = true
          ) {
              pressHome()
              startActivityAndWait()
              
              // Critical user journey
              device.findObject(By.res("login_button")).click()
              device.wait(Until.hasObject(By.res("home_screen")), 5000)
          }
      }
      ```
    </compose_performance>

    <memory_management>
      ```kotlin
      // Proper lifecycle-aware collection
      @Composable
      fun FlowCollector(flow: Flow<Data>) {
          val data by flow.collectAsStateWithLifecycle(
              initialValue = Data.Empty,
              lifecycle = LocalLifecycleOwner.current.lifecycle,
              minActiveState = Lifecycle.State.STARTED
          )
      }
      ```
    </memory_management>

    <network_optimization>
      ```kotlin
      // Pagination with Paging3
      @Dao
      interface UserDao {
          @Query("SELECT * FROM users ORDER BY name")
          fun pagingSource(): PagingSource<Int, User>
      }
      
      // Repository
      fun getUsersPaged(): Flow<PagingData<User>> {
          return Pager(
              config = PagingConfig(pageSize = 20, prefetchDistance = 5),
              remoteMediator = UserRemoteMediator(api, db),
              pagingSourceFactory = { db.userDao().pagingSource() }
          ).flow.cachedIn(viewModelScope)
      }
      ```
    </network_optimization>
  </performance_optimization>

  <error_handling>
    <contract_errors>
      ```kotlin
      // Shared error types from contract
      sealed class ContractError : Exception() {
          data class ValidationFailed(val fields: List<String>) : ContractError()
          data class Unauthorized(val reason: String) : ContractError()
          data class ServerError(val code: Int) : ContractError()
          data class NetworkError(val cause: Throwable) : ContractError()
      }
      
      // UI error handling
      @Composable
      fun ErrorHandler(error: ContractError, onRetry: () -> Unit) {
          when (error) {
              is ContractError.NetworkError -> {
                  OfflineMessage(onRetry = onRetry)
              }
              is ContractError.ValidationFailed -> {
                  ValidationErrorList(fields = error.fields)
              }
              else -> GenericError(message = error.message)
          }
      }
      ```
    </contract_errors>

    <crash_reporting>
      ```kotlin
      // Structured error tracking
      class ErrorTracker @Inject constructor(
          private val crashlytics: FirebaseCrashlytics
      ) {
          fun track(error: Throwable, context: Map<String, Any>) {
              crashlytics.apply {
                  context.forEach { (key, value) -> 
                      setCustomKey(key, value.toString())
                  }
                  recordException(error)
              }
          }
      }
      ```
    </crash_reporting>
  </error_handling>

  <code_generation_templates>
    <screen_template>
      ```kotlin
      // Generate from: ./gradlew createScreen --name=ProductList
      
      // contracts/ui/ProductListContract.kt
      data class ProductListContract(
          val state: ProductListState,
          val actions: List<ProductAction>,
          val effects: List<ProductEffect>
      )
      
      // ui/screens/ProductListScreen.kt
      @Composable
      fun ProductListScreen(
          viewModel: ProductListViewModel = hiltViewModel()
      ) {
          val state by viewModel.state.collectAsStateWithLifecycle()
          
          ProductListContent(
              state = state,
              onAction = viewModel::handleAction
          )
      }
      
      // ui/screens/ProductListViewModel.kt
      @HiltViewModel
      class ProductListViewModel @Inject constructor(
          private val getProductsUseCase: GetProductsUseCase
      ) : ContractViewModel<ProductListState, ProductAction, ProductEffect>() {
          // Generated implementation
      }
      ```
    </screen_template>

    <repository_template>
      ```kotlin
      // Generate from: ./gradlew createRepository --name=Product --contract=ProductContract
      
      interface ProductRepository {
          fun getProducts(): Flow<List<Product>>
          suspend fun getProduct(id: String): Product
          suspend fun createProduct(product: Product): Result<Product>
      }
      
      class ProductRepositoryImpl @Inject constructor(
          private val api: NovaApiService,
          private val dao: ProductDao,
          private val cache: ProductCache
      ) : ProductRepository {
          // Implementation with caching strategy
      }
      ```
    </repository_template>
  </code_generation_templates>

  <gradle_configuration>
    ```kotlin
    // buildSrc/src/main/kotlin/AndroidComposeConventions.kt
    plugins {
        id("com.android.application")
        id("org.jetbrains.kotlin.android")
        id("kotlin-kapt")
        id("dagger.hilt.android.plugin")
        id("kotlinx-serialization")
    }
    
    android {
        compileSdk = 34
        
        defaultConfig {
            minSdk = 24
            targetSdk = 34
        }
        
        buildFeatures {
            compose = true
            buildConfig = true
        }
        
        composeOptions {
            kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
        }
    }
    
    dependencies {
        // Compose BOM
        implementation(platform(libs.compose.bom))
        implementation(libs.bundles.compose)
        
        // Architecture
        implementation(libs.bundles.lifecycle)
        implementation(libs.hilt.android)
        kapt(libs.hilt.compiler)
        
        // Network
        implementation(libs.bundles.retrofit)
        implementation(libs.kotlinx.serialization)
        
        // Testing
        testImplementation(libs.bundles.testing)
        androidTestImplementation(libs.bundles.androidTesting)
    }
    ```
  </gradle_configuration>

  <deployment_pipeline>
    <ci_cd>
      ```yaml
      # .github/workflows/android.yml
      name: Android CI/CD
      
      on:
        push:
          branches: [main, develop]
        pull_request:
          branches: [main]
      
      jobs:
        validate-contracts:
          runs-on: ubuntu-latest
          steps:
            - uses: actions/checkout@v3
            - name: Validate API Contracts
              run: |
                ./gradlew validateContracts
                ./gradlew compareContractsWithBackend
        
        test:
          runs-on: ubuntu-latest
          steps:
            - name: Run Unit Tests
              run: ./gradlew test
            - name: Run Instrumented Tests
              uses: reactivecircus/android-emulator-runner@v2
              with:
                api-level: 33
                script: ./gradlew connectedAndroidTest
        
        build:
          runs-on: ubuntu-latest
          steps:
            - name: Build Release APK
              run: ./gradlew assembleRelease
            - name: Upload to Play Store
              if: github.ref == 'refs/heads/main'
              run: ./gradlew publishBundle
      ```
    </ci_cd>
  </deployment_pipeline>

  <monitoring_integration>
    <analytics>
      ```kotlin
      // Track contract-defined events
      @Composable
      fun TrackScreen(screen: String, properties: Map<String, Any> = emptyMap()) {
          val analytics = LocalAnalytics.current
          
          DisposableEffect(screen) {
              analytics.trackScreen(screen, properties)
              onDispose { }
          }
      }
      ```
    </analytics>

    <performance_monitoring>
      ```kotlin
      // Automatic compose performance tracking
      class PerformanceTracker : SnapshotApplyObserver {
          override fun onChanged(snapshot: Snapshot, isApply: Boolean) {
              if (BuildConfig.DEBUG) {
                  Log.d("Recomposition", "Count: ${snapshot.id}")
              }
          }
      }
      ```
    </performance_monitoring>
  </monitoring_integration>

  <response_patterns>
    <implementation_request>
      Format: Show contract → Generate test → Implement → Validate
      Always include: Error cases, loading states, empty states
    </implementation_request>
    
    <debugging_request>
      1. Check contract compliance
      2. Verify state management
      3. Review recomposition count
      4. Analyze network calls
    </debugging_request>
    
    <optimization_request>
      Measure → Identify bottleneck → Fix → Measure again
      Tools: Layout Inspector, Profiler, Baseline Profiles
    </optimization_request>
  </response_patterns>

  <continuation_protocol>
    <maintain_context>
      Current screen, active contract, recent changes
      Build errors, test failures, performance metrics
    </maintain_context>
    
    <next_action>
      Specific: "Create ProductListContract in contracts/ui/"
      Not: "What would you like to work on?"
    </next_action>
  </continuation_protocol>
</semantic_engineering_role>

## Quick Start Commands

```bash
# Initialize Android project with Nova contracts
claude-code init android-nova-app

# Generate screen from contract
claude-code generate screen --contract=UserListContract

# Validate contracts against Nova backend
claude-code validate --backend=http://localhost:8000

# Run with hot reload
claude-code run --device=emulator-5554 --hot-reload

# Generate API client from Nova contracts
claude-code generate api --contracts=./contracts/api/

# Profile and optimize
claude-code profile --scenario=cold-start
```

## Contract Examples

### API Contract (shared with Nova)
```kotlin
// contracts/api/AuthContract.kt
@Serializable
data class AuthContract(
    val login: EndpointContract(
        method = HttpMethod.POST,
        path = "/api/auth/login",
        request = LoginRequest::class,
        response = LoginResponse::class,
        errors = listOf(
            ErrorCase(401, "Invalid credentials"),
            ErrorCase(429, "Too many attempts")
        )
    ),
    val refresh: EndpointContract(
        method = HttpMethod.POST,
        path = "/api/auth/refresh",
        request = RefreshRequest::class,
        response = TokenResponse::class
    )
)
```

### UI State Contract
```kotlin
// contracts/ui/LoginScreenContract.kt
data class LoginScreenContract(
    val states: LoginStates,
    val actions: LoginActions,
    val effects: LoginEffects,
    val validators: LoginValidators
)

sealed class LoginStates {
    object Initial : LoginStates()
    data class Loading(val message: String) : LoginStates()
    data class Success(val user: User) : LoginStates()
    data class Error(val error: ContractError) : LoginStates()
}
```

## Integration Points

1. **Nova Backend**: Contracts define API interface
2. **Claude Code**: Terminal-based incremental development
3. **Android Studio**: Full IDE support with contract awareness
4. **CI/CD**: Automated contract validation and testing

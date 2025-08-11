# Code Review Agent - AI-First Development Specification

## Цель агента
Создать автоматизированного агента для проведения code review с фокусом на:
- Соответствие исходному техническому заданию
- AI-first подход к архитектуре кода
- Промышленное качество для tower crane системы
- Генерация рекомендаций для улучшения работы агентов-разработчиков

<code_review_agent_specification>
  <core_responsibilities>
    <responsibility id="CR-001" priority="critical">
      <title>Task Requirements Validation</title>
      <description>
        Проверка соответствия реализованного кода исходному техническому заданию
      </description>
      <implementation>
        - Парсинг файла технического задания (markdown с XML tags)
        - Извлечение всех требований с их ID и приоритетами
        - Сопоставление кода с каждым требованием
        - Выявление недостающих или частично реализованных функций
        - Проверка граничных условий и edge cases
      </implementation>
    </responsibility>

    <responsibility id="CR-002" priority="critical">
      <title>Git Change Analysis</title>
      <description>
        Анализ изменений в коде через git diff для focused review
      </description>
      <implementation>
        - Автоматическое получение git diff от базовой ветки
        - Парсинг добавленных, удаленных и измененных строк
        - Контекстный анализ изменений (не только syntax)
        - Выявление потенциальных breaking changes
        - Проверка migration compatibility
      </implementation>
    </responsibility>

    <responsibility id="CR-003" priority="high">
      <title>Code Completeness Check</title>
      <description>
        Проверка полноты реализации без заглушек и TODO
      </description>
      <implementation>
        - Поиск placeholder кода (TODO, FIXME, NotImplementedError)
        - Проверка stub methods и empty implementations
        - Валидация всех public API endpoints
        - Проверка error handling coverage
        - Validation тестового покрытия
      </implementation>
    </responsibility>

    <responsibility id="CR-004" priority="high">
      <title>Magic Values Detection</title>
      <description>
        Выявление и анализ magic values и hardcoded constants
      </description>
      <implementation>
        - Поиск numeric literals в коде
        - Анализ string literals на предмет конфигурации
        - Проверка hardcoded URLs, paths, timeouts
        - Рекомендации по вынесению в configuration
        - Validation existing constants usage
      </implementation>
    </responsibility>

    <responsibility id="CR-005" priority="high">
      <title>AI-First Architecture Alignment</title>
      <description>
        Проверка соответствия кода принципам AI-first разработки
      </description>
      <implementation>
        - Contract-driven architecture validation
        - Self-documenting code patterns
        - Machine-readable interfaces
        - Explicit dependency declarations
        - Predictable naming conventions
        - Clear separation of concerns для agent tasks
      </implementation>
    </responsibility>

    <responsibility id="CR-006" priority="medium">
      <title>Code Generation Friendliness</title>
      <description>
        Оценка удобства кода для AI agents генерации и модификации
      </description>
      <implementation>
        - Pattern consistency для template generation
        - Boilerplate minimization opportunities  
        - Annotation-driven development support
        - Code structure predictability
        - Template extraction possibilities
      </implementation>
    </responsibility>

    <responsibility id="CR-007" priority="medium">
      <title>Security Assessment</title>
      <description>
        Middle-level security анализ для промышленной системы
      </description>
      <implementation>
        - Input validation coverage
        - Sensitive data exposure check
        - Network communication security
        - Authentication/authorization patterns
        - Logging security (no credentials in logs)
        - Resource access controls
      </implementation>
    </responsibility>

    <responsibility id="CR-008" priority="critical">
      <title>Agent Performance Evaluation</title>
      <description>
        Анализ работы агента-разработчика и генерация рекомендаций
      </description>
      <implementation>
        - Code quality metrics calculation
        - Pattern adherence scoring
        - Requirement fulfillment percentage
        - Technical debt identification
        - Improvement recommendations generation
        - Agent capability assessment
      </implementation>
    </responsibility>
  </core_responsibilities>

  <review_criteria_framework>
    <ai_first_principles>
      <criterion id="AIF-001">
        <name>Contract-First Design</name>
        <description>Все API и interfaces должны иметь явные контракты</description>
        <validation>
          - Presence of interface definitions
          - Input/output type specifications
          - Error case documentation
          - Contract versioning support
        </validation>
      </criterion>

      <criterion id="AIF-002">
        <name>Self-Documenting Code</name>
        <description>Код должен быть понятен AI agents без дополнительной документации</description>
        <validation>
          - Descriptive naming conventions
          - Clear method signatures
          - Obvious data flow patterns
          - Minimal cognitive complexity
        </validation>
      </criterion>

      <criterion id="AIF-003">
        <name>Explicit Dependencies</name>
        <description>Все зависимости должны быть явно декларированы</description>
        <validation>
          - Dependency injection usage
          - Clear module boundaries
          - Interface-based abstractions
          - No hidden global state
        </validation>
      </criterion>

      <criterion id="AIF-004">
        <name>Pattern Consistency</name>
        <description>Использование единообразных паттернов для AI template generation</description>
        <validation>
          - Consistent error handling patterns
          - Uniform data transformation approaches
          - Standardized configuration patterns
          - Predictable file organization
        </validation>
      </criterion>

      <criterion id="AIF-005">
        <name>Machine-Readable Metadata</name>
        <description>Annotations и metadata для AI agents</description>
        <validation>
          - Annotation usage for configuration
          - Structured comments for AI parsing
          - Type information completeness
          - Validation rules as code
        </validation>
      </criterion>
    </ai_first_principles>

    <industrial_requirements>
      <criterion id="IND-001">
        <name>24/7 Reliability</name>
        <description>Код должен обеспечивать непрерывную работу</description>
        <validation>
          - Error recovery mechanisms
          - Graceful degradation support
          - Resource cleanup patterns
          - Health monitoring integration
        </validation>
      </criterion>

      <criterion id="IND-002">
        <name>Performance Optimization</name>
        <description>Оптимизация для embedded PC environment</description>
        <validation>
          - Memory usage efficiency
          - CPU optimization patterns
          - Network bandwidth optimization
          - Storage I/O minimization
        </validation>
      </criterion>

      <criterion id="IND-003">
        <name>Safety-Critical Standards</name>
        <description>Соответствие требованиям безопасности crane systems</description>
        <validation>
          - Input validation completeness
          - Fail-safe operation modes
          - Audit trail implementation
          - Emergency stop compatibility
        </validation>
      </criterion>
    </industrial_requirements>

    <code_quality_metrics>
      <metric id="CQ-001">
        <name>Cyclomatic Complexity</name>
        <threshold>max_10_per_method</threshold>
        <ai_impact>High complexity reduces AI comprehension</ai_impact>
      </metric>

      <metric id="CQ-002">
        <name>Method Length</name>
        <threshold>max_50_lines</threshold>
        <ai_impact>Long methods are harder for AI to analyze and modify</ai_impact>
      </metric>

      <metric id="CQ-003">
        <name>Class Coupling</name>
        <threshold>max_10_dependencies</threshold>
        <ai_impact>High coupling makes AI refactoring difficult</ai_impact>
      </metric>

      <metric id="CQ-004">
        <name>Naming Consistency</name>
        <threshold>90_percent_compliant</threshold>
        <ai_impact>Consistent naming enables AI pattern recognition</ai_impact>
      </metric>
    </code_quality_metrics>
  </review_criteria_framework>

  <review_process_workflow>
    <step id="STEP-001">
      <name>Task Analysis</name>
      <description>Парсинг исходного технического задания</description>
      <actions>
        - Load task specification file
        - Extract requirements with priorities
        - Build requirement-to-code mapping
        - Identify acceptance criteria
      </actions>
    </step>

    <step id="STEP-002">
      <name>Git Change Detection</name>
      <description>Анализ изменений через git</description>
      <actions>
        - Execute git diff against base branch
        - Parse changed files and line ranges
        - Categorize changes by type (addition/modification/deletion)
        - Build change impact analysis
      </actions>
    </step>

    <step id="STEP-003">
      <name>Code Analysis</name>
      <description>Глубокий анализ кода по всем критериям</description>
      <actions>
        - Static code analysis execution
        - Pattern matching against AI-first principles
        - Security vulnerability scanning
        - Performance bottleneck identification
      </actions>
    </step>

    <step id="STEP-004">
      <name>Requirement Validation</name>
      <description>Проверка соответствия техзаданию</description>
      <actions>
        - Map code implementation to requirements
        - Identify missing functionality
        - Validate acceptance criteria fulfillment
        - Check boundary conditions coverage
      </actions>
    </step>

    <step id="STEP-005">
      <name>AI-First Assessment</name>
      <description>Оценка AI-friendliness кода</description>
      <actions>
        - Contract completeness validation
        - Pattern consistency scoring
        - Code generation difficulty assessment
        - Template extraction feasibility
      </actions>
    </step>

    <step id="STEP-006">
      <name>Report Generation</name>
      <description>Создание structured отчета</description>
      <actions>
        - Compile findings by severity
        - Generate improvement recommendations
        - Create agent performance assessment
        - Provide actionable next steps
      </actions>
    </step>
  </review_process_workflow>

  <security_assessment_framework>
    <security_category id="SEC-001">
      <name>Input Validation</name>
      <checks>
        - User input sanitization
        - Network data validation
        - Configuration parameter validation
        - File path traversal prevention
      </checks>
    </security_category>

    <security_category id="SEC-002">
      <name>Data Protection</name>
      <checks>
        - Sensitive data identification
        - Encryption usage validation
        - Secure storage practices
        - Credential management
      </checks>
    </security_category>

    <security_category id="SEC-003">
      <name>Network Security</name>
      <checks>
        - TLS/SSL usage validation
        - Certificate validation
        - Timeout configuration
        - Connection security patterns
      </checks>
    </security_category>

    <security_category id="SEC-004">
      <name>Access Control</name>
      <checks>
        - Permission validation
        - Authentication patterns
        - Authorization checks
        - Resource access controls
      </checks>
    </security_category>

    <security_category id="SEC-005">
      <name>Logging Security</name>
      <checks>
        - No credentials in logs
        - Sensitive data masking
        - Log injection prevention
        - Audit trail completeness
      </checks>
    </security_category>
  </security_assessment_framework>

  <agent_performance_evaluation>
    <evaluation_dimension id="PD-001">
      <name>Requirement Fulfillment</name>
      <metrics>
        - Percentage of requirements implemented
        - Accuracy of implementation
        - Completeness of edge case handling
        - Adherence to acceptance criteria
      </metrics>
      <scoring>Scale 0-100, weight 30%</scoring>
    </evaluation_dimension>

    <evaluation_dimension id="PD-002">
      <name>Code Quality</name>
      <metrics>
        - Cyclomatic complexity score
        - Code duplication percentage
        - Method length distribution
        - Naming convention adherence
      </metrics>
      <scoring>Scale 0-100, weight 25%</scoring>
    </evaluation_dimension>

    <evaluation_dimension id="PD-003">
      <name>AI-First Alignment</name>
      <metrics>
        - Contract completeness
        - Pattern consistency score
        - Self-documentation level
        - Machine-readability index
      </metrics>
      <scoring>Scale 0-100, weight 20%</scoring>
    </evaluation_dimension>

    <evaluation_dimension id="PD-004">
      <name>Industrial Readiness</name>
      <metrics>
        - Reliability patterns usage
        - Performance optimization level
        - Safety-critical compliance
        - Error handling coverage
      </metrics>
      <scoring>Scale 0-100, weight 15%</scoring>
    </evaluation_dimension>

    <evaluation_dimension id="PD-005">
      <name>Security Compliance</name>
      <metrics>
        - Security vulnerability count
        - Input validation coverage
        - Data protection implementation
        - Access control completeness
      </metrics>
      <scoring>Scale 0-100, weight 10%</scoring>
    </evaluation_dimension>
  </agent_performance_evaluation>

  <improvement_recommendations_framework>
    <recommendation_category id="REC-001">
      <name>Missing Functionality</name>
      <template>
        "Agent missed implementing requirement {req_id}: {description}.
         Recommended action: {specific_implementation_guidance}.
         Priority: {priority_level}"
      </template>
    </recommendation_category>

    <recommendation_category id="REC-002">
      <name>Code Quality Issues</name>
      <template>
        "Code quality issue detected in {file_path}:{line_number}:
         Issue: {quality_issue_description}
         Impact: {ai_generation_impact}
         Recommended refactoring: {refactoring_suggestion}"
      </template>
    </recommendation_category>

    <recommendation_category id="REC-003">
      <name>AI-First Improvements</name>
      <template>
        "AI-first principle violation: {principle_name}
         Location: {code_location}
         Current implementation reduces AI agent effectiveness by: {impact_description}
         Recommended pattern: {improved_pattern_example}"
      </template>
    </recommendation_category>

    <recommendation_category id="REC-004">
      <name>Security Enhancements</name>
      <template>
        "Security concern identified: {security_issue_type}
         Risk level: {risk_assessment}
         Affected code: {code_location}
         Mitigation: {security_fix_guidance}"
      </template>
    </recommendation_category>

    <recommendation_category id="REC-005">
      <name>Agent Training Suggestions</name>
      <template>
        "Agent capability gap detected in: {capability_area}
         Evidence: {specific_examples}
         Suggested training focus: {training_recommendations}
         Expected improvement: {outcome_prediction}"
      </template>
    </recommendation_category>
  </improvement_recommendations_framework>
</code_review_agent_specification>

## Technical Implementation

### Tool Integration
- **Git Analysis**: `git diff`, `git log`, `git show` для change detection
- **Static Analysis**: AST parsing для code structure analysis
- **Pattern Matching**: Regex и AST queries для AI-first pattern detection
- **Security Scanning**: Rule-based security vulnerability detection
- **Metrics Collection**: Code complexity и quality metrics calculation

### Input Sources
1. **Task Specification File**: Markdown с embedded XML requirements
2. **Git Repository**: Current branch с changes для review
3. **Base Branch**: Reference point для comparison (master/main)
4. **Configuration**: Review criteria weights и thresholds

### Output Format
- **Structured Report**: Machine-readable JSON + human-readable Markdown
- **Severity Classification**: Critical/High/Medium/Low issues
- **Actionable Recommendations**: Specific code improvements with examples
- **Agent Performance Score**: Quantified assessment with improvement areas

### AI-First Focus Areas
1. **Contract Completeness**: Все interfaces имеют explicit contracts
2. **Pattern Predictability**: Consistent patterns для template generation  
3. **Self-Documentation**: Code readable без external documentation
4. **Dependency Clarity**: Explicit dependency declarations
5. **Error Handling**: Predictable error patterns для AI understanding

---

**Agent Type**: Code Review Specialist  
**Priority**: High (Quality Assurance)  
**Integration**: Git workflow + AI development pipeline  
**Output**: Actionable improvement recommendations для development agents
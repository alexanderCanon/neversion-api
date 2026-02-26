# MY HEXAGONAL ARCHITECTURE FOR SPRING PROJECTS

src/main/java/com/example/project
│
├── config/ (Security global config)
│
├── exceptions/
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   └── CustomException.java
│
└── <feature-name>/
    ├── domain/
    │   ├── model/
    │   │   ├── <AggregateName>.java
    │   │   └── <EnumName>.java
    │   │
    │   ├── port/
    │   │   └── out/
    │   │       └── <Feature>RepositoryPort.java
    │   │
    │   └── service/
    │       └── <DomainService>.java
    │
    ├── application/
    │   ├── port/
    │   │   └── in/
    │   │       └── <UseCaseName>.java
    │   │
    │   └── service/
    │       └── <UseCaseName>Service.java
    │
    └── infrastructure/
        ├── config/
        │   └── SecurityConfig.java
        │
        └── adapters/
            ├── in/
            │   └── rest/
            │       ├── <Feature>Controller.java
            │       │
            │       ├── dto/
            │       │   ├── <RequestDTO>.java
            │       │   └── <ResponseDTO>.java
            │       │
            │       └── mapper/
            │           └── <RestMapper>.java
            │
            └── out/
                ├── <Feature>Entity.java
                ├── SpringData<Feature>Repository.java
                ├── Jpa<Feature>Adapter.java
                └── <Feature>PersistenceMapper.java
# MY HEXAGONAL ARCHITECTURE FOR SPRING PROJECTS
Don't create any Java files, just packages structure, neither .gitkeep files


src/main/java/com/example/project
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
    │   │       └── RepositoryPort.java
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
                ├── Entity.java
                ├── RepositoryAdapter.java
                ├── JpaAdapter.java
                └── PersistenceMapper.java
IF OBJECT_ID('sys_tenant', 'U') IS NOT NULL
  DROP TABLE sys_tenant;
GO
CREATE TABLE sys_tenant
(
    id                    bigint                          NOT NULL,
    tenant_id             nvarchar(20)                    NOT NULL,
    contact_user_name     nvarchar(255)                   NULL,
    contact_phone         nvarchar(20)                    NULL,
    company_name          nvarchar(255)                   NULL,
    license_number        nvarchar(30)                    NULL,
    address               nvarchar(200)                   NULL,
    intro                 nvarchar(200)                   NULL,
    domain                nvarchar(200)                   NULL,
    remark                nvarchar(200)                   NULL,
    package_id            bigint                          NULL,
    expire_time           datetime2(7)                    NULL,
    account_count         int             DEFAULT ((-1))  NULL,
    status                nchar(1)        DEFAULT ('0')   NULL,
    del_flag              nchar(1)        DEFAULT ('0')   NULL,
    create_dept           bigint                          NULL,
    create_by             bigint                          NULL,
    create_time           datetime2(7)                    NULL,
    update_by             bigint                          NULL,
    update_time           datetime2(7)                    NULL,
    CONSTRAINT PK__sys_tenant__B21E8F2427725F8A PRIMARY KEY CLUSTERED (id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

INSERT sys_tenant VALUES (1, N'000000', N'Management group', N'0969753414', N'XXX Limited', NULL, NULL, N'Multi-tenant general background management system', NULL, NULL, NULL, NULL, -1, N'0', N'0', 103, 1, getdate(), NULL, NULL)
GO

IF OBJECT_ID('sys_tenant_package', 'U') IS NOT NULL
  DROP TABLE sys_tenant_package;
GO
CREATE TABLE sys_tenant_package
(
    package_id            bigint                          NOT NULL,
    package_name          nvarchar(20)                    NOT NULL,
    menu_ids              nvarchar(20)                    NULL,
    remark                nvarchar(200)                   NULL,
    menu_check_strictly   tinyint         DEFAULT ((1))   NULL,
    status                nchar(1)        DEFAULT ('0')   NULL,
    del_flag              nchar(1)        DEFAULT ('0')   NULL,
    create_dept           bigint                          NULL,
    create_by             bigint                          NULL,
    create_time           datetime2(7)                    NULL,
    update_by             bigint                          NULL,
    update_time           datetime2(7)                    NULL,
    CONSTRAINT PK__sys_tenant_package__B21E8F2427725F8A PRIMARY KEY CLUSTERED (package_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('sys_dept', 'U') IS NOT NULL
  DROP TABLE sys_dept;
GO
CREATE TABLE sys_dept
(
    dept_id     bigint                     NOT NULL,
    tenant_id   nvarchar(20) DEFAULT ('000000') NULL,
    parent_id   bigint       DEFAULT ((0)) NULL,
    ancestors   nvarchar(500)DEFAULT ''    NULL,
    dept_name   nvarchar(255) DEFAULT ''    NULL,
    order_num   int          DEFAULT ((0)) NULL,
    leader      nvarchar(20)               NULL,
    phone       nvarchar(11)               NULL,
    email       nvarchar(255)               NULL,
    status      nchar(1)     DEFAULT ('0') NULL,
    del_flag    nchar(1)     DEFAULT ('0') NULL,
    create_dept bigint                     NULL,
    create_by   bigint                     NULL,
    create_time datetime2(7)               NULL,
    update_by   bigint                     NULL,
    update_time datetime2(7)               NULL,
    CONSTRAINT PK__sys_dept__DCA659747DE13804 PRIMARY KEY CLUSTERED (dept_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

INSERT sys_dept VALUES (100, N'000000', 0, N'0', N'Algorithm Arena', 0, N'Administrator', N'0969753414', N'algo-arena@gmail.com', N'0', N'0', 103, 1, getdate(), NULL, NULL)
GO
INSERT sys_dept VALUES (101, N'000000', 100, N'0,100', N'Quản lý phòng thi', 1, N'Nguyễn Ngọc Phú', N'0775545294', N'phunguyen@gmail.com', N'0', N'0', 103, 1, getdate(), NULL, NULL)
GO
INSERT sys_dept VALUES (102, N'000000', 100, N'0,100', N'Quản lý bài toán', 2, N'Nguyễn Văn Cường', N'0346844010', N'cuongnguyen@gmail.com', N'0', N'0', 103, 1, getdate(), NULL, NULL)
GO
INSERT sys_dept VALUES (103, N'000000', 100, N'0,100', N'Quản lý bài làm', 2, N'Trần Văn Hữu', N'0383609548', N'huutran@gmail.com', N'0', N'0', 103, 1, getdate(), NULL, NULL)
GO
INSERT sys_dept VALUES (104, N'000000', 100, N'0,100', N'Quản lý doanh thu', 2, N'Nguyễn Minh Quốc', N'0933071554', N'quocnguyen@gmail.com', N'0', N'0', 103, 1, getdate(), NULL, NULL)
GO

IF OBJECT_ID('sys_notice', 'U') IS NOT NULL
  DROP TABLE sys_notice;
GO
CREATE TABLE sys_notice
(
    notice_id      bigint                     NOT NULL,
    tenant_id      nvarchar(20) DEFAULT ('000000') NULL,
    notice_title   nvarchar(255)               NOT NULL,
    notice_type    nchar(1)                   NOT NULL,
    notice_content nvarchar(max)              NULL,
    status         nchar(1)     DEFAULT ('0') NULL,
    create_dept    bigint                     NULL,
    create_by      bigint                     NULL,
    create_time    datetime2(7)               NULL,
    update_by      bigint                     NULL,
    update_time    datetime2(7)               NULL,
    remark         nvarchar(255)              NULL,
    CONSTRAINT PK__sys_noti__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (notice_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
TEXTIMAGE_ON [PRIMARY]
GO

INSERT sys_notice VALUES (1, N'000000', N'Reminder: 2023-11-20 A new version has been released', N'4', N'New version content', N'0', 103, 1, getdate(), NULL, NULL, N'administrator')
GO
INSERT sys_notice VALUES (2, N'000000', N'Maintenance notice: 2023-11-01 Star algoarena system maintenance in the early morning', N'4', N'Maintenance content', N'0', 103, 1, getdate(), NULL, NULL, N'administrator')
GO

IF OBJECT_ID('sys_post', 'U') IS NOT NULL
  DROP TABLE sys_post;
GO
CREATE TABLE sys_post
(
    post_id     bigint                          NOT NULL,
    tenant_id   nvarchar(20) DEFAULT ('000000') NULL,
    post_code   nvarchar(64)                    NOT NULL,
    post_name   nvarchar(255)                   NOT NULL,
    post_sort   int                             NOT NULL,
    status      nchar(1)                        NOT NULL,
    create_dept bigint                          NULL,
    create_by   bigint                          NULL,
    create_time datetime2(7)                    NULL,
    update_by   bigint                          NULL,
    update_time datetime2(7)                    NULL,
    remark      nvarchar(500)                   NULL,
    CONSTRAINT PK__sys_post__3ED7876668E2D081 PRIMARY KEY CLUSTERED (post_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

INSERT sys_post VALUES (1, N'000000', N'super-management', N'Quản lý cấp cao', 1, N'0', 103, 1, getdate(), NULL, NULL, N'')
GO
INSERT sys_post VALUES (2, N'000000', N'management', N'Quản lý', 2, N'0', 103, 1, getdate(), NULL, NULL, N'')
GO
INSERT sys_post VALUES (3, N'000000', N'staff', N'Nhân viên', 3, N'0', 103, 1, getdate(), NULL, NULL, N'')
GO

IF OBJECT_ID('sys_user', 'U') IS NOT NULL
  DROP TABLE sys_user;
GO
CREATE TABLE sys_user
(
    user_id     bigint                             NOT NULL,
    tenant_id   nvarchar(20)  DEFAULT ('000000')   NULL,
    dept_id     bigint                             NULL,
    user_name   nvarchar(40)                       NOT NULL,
    nick_name   nvarchar(255)                      NOT NULL,
    user_type   nvarchar(10)  DEFAULT ('sys_user') NULL,
    email       nvarchar(50)  DEFAULT ''           NULL,
    phonenumber nvarchar(11)  DEFAULT ''           NULL,
    sex         nchar(1)      DEFAULT ('0')        NULL,
    avatar      bigint                             NULL,
    password    nvarchar(100) DEFAULT ''           NULL,
    status      nchar(1)      DEFAULT ('0')        NULL,
    del_flag    nchar(1)      DEFAULT ('0')        NULL,
    login_ip    nvarchar(128) DEFAULT ''           NULL,
    login_date  datetime2(7)                       NULL,
    create_dept bigint                             NULL,
    create_by   bigint                             NULL,
    create_time datetime2(7)                       NULL,
    update_by   bigint                             NULL,
    update_time datetime2(7)                       NULL,
    remark      nvarchar(500)                      NULL,
    system_type nvarchar(30)  DEFAULT ('system')   NOT NULL,
    CONSTRAINT PK__sys_user__B9BE370F79170B6A PRIMARY KEY CLUSTERED (user_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

INSERT sys_user VALUES (1, N'000000', 100, N'admin', N'Administrator', N'sys_user', N'admin@gmail.com', N'0987654321', N'1', NULL, N'$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', N'0', N'0', N'127.0.0.1', getdate(), 103, 1, getdate(), NULL, NULL, N'administrator', N'system')
GO
INSERT sys_user VALUES (2, N'000000', 103, N'hoald1', N'Lê Đình Hòa', N'sys_user', N'hoald1@gmail.com', N'0999999999', N'1', NULL, N'$2a$10$ASuKduzRhpJdv2be3MQWi.PIxMeNMokIfKJLWc2cP3k0sVhXEtb0K', N'0', N'0', N'127.0.0.1', getdate(), 103, 1, getdate(), NULL, NULL, N'', N'system')
GO
INSERT sys_user VALUES (3, N'000000', 103, N'hoald2', N'Lê Đình Hòa', N'sys_user', N'hoald2@gmail.com', N'0999999999', N'1', NULL, N'$2a$10$ASuKduzRhpJdv2be3MQWi.PIxMeNMokIfKJLWc2cP3k0sVhXEtb0K', N'0', N'0', N'127.0.0.1', getdate(), 103, 1, getdate(), NULL, NULL, N'', N'system')
GO
INSERT sys_user VALUES (4, N'000000', 103, N'hoald3', N'Lê Đình Hòa', N'sys_user', N'hoald3@gmail.com', N'0999999999', N'1', NULL, N'$2a$10$ASuKduzRhpJdv2be3MQWi.PIxMeNMokIfKJLWc2cP3k0sVhXEtb0K', N'0', N'0', N'127.0.0.1', getdate(), 103, 1, getdate(), NULL, NULL, N'', N'system')
GO

IF OBJECT_ID('sys_logininfor', 'U') IS NOT NULL
  DROP TABLE sys_logininfor;
GO
CREATE TABLE sys_logininfor
(
    info_id        bigint                      NOT NULL,
    tenant_id      nvarchar(20)  DEFAULT ('000000') NULL,
    user_name      nvarchar(255)  DEFAULT ''    NULL,
    ipaddr         nvarchar(128) DEFAULT ''    NULL,
    login_location nvarchar(255) DEFAULT ''    NULL,
    browser        nvarchar(50)  DEFAULT ''    NULL,
    os             nvarchar(50)  DEFAULT ''    NULL,
    status         nchar(1)      DEFAULT ('0') NULL,
    msg            nvarchar(255) DEFAULT ''    NULL,
    login_time     datetime2(7)                NULL,
    CONSTRAINT PK__sys_logi__3D8A9C1A1854AE10 PRIMARY KEY CLUSTERED (info_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX idx_sys_logininfor_s ON sys_logininfor (status)
GO
CREATE NONCLUSTERED INDEX idx_sys_logininfor_lt ON sys_logininfor (login_time)
GO

IF OBJECT_ID('sys_user_post', 'U') IS NOT NULL
  DROP TABLE sys_user_post;
GO
CREATE TABLE sys_user_post
(
    user_id bigint NOT NULL,
    post_id bigint NOT NULL,
    CONSTRAINT PK__sys_user__CA534F799C04589B PRIMARY KEY CLUSTERED (user_id, post_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

INSERT sys_user_post VALUES (2, 1)
GO
INSERT sys_user_post VALUES (3, 1)
GO
INSERT sys_user_post VALUES (4, 1)
GO

IF OBJECT_ID('sys_user_role', 'U') IS NOT NULL
  DROP TABLE sys_user_role;
GO
CREATE TABLE sys_user_role
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT PK__sys_user__6EDEA153FB34D8F0 PRIMARY KEY CLUSTERED (user_id, role_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

INSERT sys_user_role VALUES (1, 1)
GO
INSERT sys_user_role VALUES (2, 1)
GO
INSERT sys_user_role VALUES (3, 1)
GO
INSERT sys_user_role VALUES (4, 1)
GO

IF OBJECT_ID('sys_oss', 'U') IS NOT NULL
  DROP TABLE sys_oss;
GO
CREATE TABLE sys_oss
(
    oss_id        bigint                          NOT NULL,
    tenant_id     nvarchar(20)  DEFAULT ('000000') NULL,
    file_name     nvarchar(255) DEFAULT ''        NOT NULL,
    original_name nvarchar(255) DEFAULT ''        NOT NULL,
    file_suffix   nvarchar(10)  DEFAULT ''        NOT NULL,
    url           nvarchar(500)                   NOT NULL,
    create_dept   bigint                          NULL,
    create_time   datetime2(7)                    NULL,
    create_by     bigint                          NULL,
    update_time   datetime2(7)                    NULL,
    update_by     bigint                          NULL,
    service       nvarchar(20)  DEFAULT ('minio') NOT NULL,
    CONSTRAINT PK__sys_oss__91241EA442389F0D PRIMARY KEY CLUSTERED (oss_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('sys_oss_config', 'U') IS NOT NULL
  DROP TABLE sys_oss_config;
GO
CREATE TABLE sys_oss_config
(
    oss_config_id bigint                      NOT NULL,
    tenant_id     nvarchar(20)  DEFAULT ('000000') NULL,
    config_key    nvarchar(20)  DEFAULT ''    NOT NULL,
    access_key    nvarchar(255) DEFAULT ''    NULL,
    secret_key    nvarchar(255) DEFAULT ''    NULL,
    bucket_name   nvarchar(255) DEFAULT ''    NULL,
    prefix        nvarchar(255) DEFAULT ''    NULL,
    endpoint      nvarchar(255) DEFAULT ''    NULL,
    domain        nvarchar(255) DEFAULT ''    NULL,
    is_https      nchar(1)      DEFAULT ('N') NULL,
    region        nvarchar(255) DEFAULT ''    NULL,
    access_policy nchar(1)      DEFAULT ('1') NOT NULL,
    status        nchar(1)      DEFAULT ('1') NULL,
    ext1          nvarchar(255) DEFAULT ''    NULL,
    create_dept   bigint                      NULL,
    create_by     bigint                      NULL,
    create_time   datetime2(7)                NULL,
    update_by     bigint                      NULL,
    update_time   datetime2(7)                NULL,
    remark        nvarchar(500)               NULL,
    CONSTRAINT PK__sys_oss___BFBDE87009ED2882 PRIMARY KEY CLUSTERED (oss_config_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

INSERT INTO sys_oss_config VALUES (N'1', N'000000', N'minio', N'admin', N'admin123', N'admin', N'', N'127.0.0.1:9000', N'127.0.0.1:9000',N'N', N'', N'1', N'0', N'', 103, 1, getdate(), 1, getdate(), NULL)
GO
INSERT INTO sys_oss_config VALUES (N'2', N'000000', N'public_document', N'admin', N'admin123', N'admin', N'', N'127.0.0.1:9000', N'127.0.0.1:9000',N'N', N'', N'1', N'0', N'', 103, 1, getdate(), 1, getdate(), NULL)
GO

IF OBJECT_ID('sys_data_history', 'U') IS NOT NULL
    DROP TABLE sys_data_history;
GO
CREATE TABLE sys_data_history
(
    id            bigint         IDENTITY(1,1)      NOT NULL,
    tenant_id     nvarchar(20)   DEFAULT ('000000')     NULL,
    ref_id        bigint                            NOT NULL,
    new_value     nvarchar(1000) DEFAULT ''             NULL,
    old_value     nvarchar(1000) DEFAULT ''             NULL,
    data_field    nvarchar(50)   DEFAULT ''             NULL,
    hist_type     nvarchar(20)   DEFAULT ''         NOT NULL,
    table_name    nvarchar(50)   DEFAULT ''         NOT NULL,
    system_type   nvarchar(30)   DEFAULT ('system') NOT NULL,
    create_dept   bigint         DEFAULT (103)      NOT NULL,
    create_by     bigint         DEFAULT (1)        NOT NULL,
    create_time   datetime2(7)                      NOT NULL,
    update_by     bigint                                NULL,
    update_time   datetime2(7)                          NULL,
    CONSTRAINT PK__sys_data_history PRIMARY KEY CLUSTERED (id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('sys_otp', 'U') IS NOT NULL
  DROP TABLE sys_otp;
GO
CREATE TABLE sys_otp
(
    id              bigint                              NOT NULL,
    tenant_id       nvarchar(20)    DEFAULT ('000000')  NULL,
    otp_code        nvarchar(10)                        NOT NULL,
    ref_id          bigint                              NOT NULL,
    phonenumber     nvarchar(11)  DEFAULT ''            NULL,
    expired_time    datetime2(7)                        NULL,
    otp_type        nvarchar(2)                         NULL,
    status          nchar(1)     DEFAULT ('0')          NULL,
    create_dept     bigint                              NULL,
    create_by       bigint                              NULL,
    create_time     datetime2(7)                        NULL,
    update_by       bigint                              NULL,
    update_time     datetime2(7)                        NULL,
    remark          nvarchar(255)                       NULL,
    system_type     nvarchar(30)  DEFAULT ('system')    NOT NULL,
    CONSTRAINT PK__sys_otp__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

/******************************** ADDITION TABLES ********************************/

IF OBJECT_ID('problem', 'U') IS NOT NULL
  DROP TABLE problem;
GO
GO
CREATE TABLE problem
(
  id                  bigint                              NOT NULL,
  tenant_id           nvarchar(20)    DEFAULT ('000000')  NULL,
  title               nvarchar(255)                       NOT NULL,
  description         ntext                               NOT NULL,
  difficulty          tinyint                             NOT NULL,
  create_dept         bigint                              NULL,
  create_by           bigint                              NULL,
  create_time         datetime2(7)                        NULL,
  update_by           bigint                              NULL,
  update_time         datetime2(7)                        NULL,
  remark              nvarchar(255)                       NULL,
  CONSTRAINT PK__problem__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
      WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
      ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('testcase', 'U') IS NOT NULL
  DROP TABLE testcase;
GO
CREATE TABLE testcase
(
  id                  bigint                              NOT NULL,
  tenant_id           nvarchar(20)    DEFAULT ('000000')  NULL,
  problem_id          bigint                              NOT NULL,
  input_data          nvarchar(255)                       NOT NULL,
  expected_output     nvarchar(255)                       NOT NULL,
  illustration        varchar(500)                        NULL,
  explanation         nvarchar(255)                       NULL,
  is_hidden           tinyint                             NOT NULL,
  create_dept         bigint                              NULL,
  create_by           bigint                              NULL,
  create_time         datetime2(7)                        NULL,
  update_by           bigint                              NULL,
  update_time         datetime2(7)                        NULL,
  remark              nvarchar(255)                       NULL,
  CONSTRAINT PK__testcase__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
      WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
      ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('exam', 'U') IS NOT NULL
  DROP TABLE exam;
GO
CREATE TABLE exam
(
  id                  bigint                              NOT NULL,
  tenant_id           nvarchar(20)    DEFAULT ('000000')  NULL,
  title               nvarchar(255)                       NOT NULL,
  description         nvarchar(500)                       NOT NULL,
  start_time          datetime2(7)                        NULL,
  end_time            datetime2(7)                        NULL,
  create_dept         bigint                              NULL,
  create_by           bigint                              NULL,
  create_time         datetime2(7)                        NULL,
  update_by           bigint                              NULL,
  update_time         datetime2(7)                        NULL,
  remark              nvarchar(255)                       NULL,
  CONSTRAINT PK__exam__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
      WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
      ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('exam_problem', 'U') IS NOT NULL
  DROP TABLE exam_problem;
GO
CREATE TABLE exam_problem
(
  id                  bigint                              NOT NULL,
  tenant_id           nvarchar(20)    DEFAULT ('000000')  NULL,
  exam_id             bigint                              NOT NULL,
  problem_id          bigint                              NOT NULL,
  max_score           tinyint                             NULL,
  CONSTRAINT PK__exam_problem__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
      WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
      ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('customer', 'U') IS NOT NULL
  DROP TABLE customer;
GO
CREATE TABLE customer
(
  user_id             bigint                              NOT NULL,
  tenant_id           nvarchar(20)  DEFAULT ('000000')    NULL,
  customer_type       nvarchar(20)  DEFAULT ('sys_customer')  NULL,
  user_name           nvarchar(40)                        NOT NULL,
  password            nvarchar(100) DEFAULT ''            NULL,
  nick_name           nvarchar(255)                       NOT NULL,
  email               nvarchar(50)  DEFAULT ''            NULL,
  phonenumber         nvarchar(11)  DEFAULT ''            NULL,
  sex                 nchar(1)      DEFAULT ('0')         NULL,
  avatar              bigint                              NULL,
  status              nchar(1)      DEFAULT ('0')         NULL,
  create_dept         bigint                              NULL,
  create_by           bigint                              NULL,
  create_time         datetime2(7)                        NULL,
  update_by           bigint                              NULL,
  update_time         datetime2(7)                        NULL,
  remark              nvarchar(500)                       NULL,
  CONSTRAINT PK__customer__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (user_id)
      WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
      ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('submission', 'U') IS NOT NULL
  DROP TABLE submission;
GO
CREATE TABLE submission
(
  id                  bigint                              NOT NULL,
  tenant_id           nvarchar(20)    DEFAULT ('000000')  NULL,
  exam_id             bigint                              NULL,
  problem_id          bigint                              NOT NULL,
  user_id             bigint                              NOT NULL,
  code                ntext                               NOT NULL,
  status              tinyint                             NOT NULL,
  score               tinyint                             NULL,
  create_dept         bigint                              NULL,
  create_by           bigint                              NULL,
  create_time         datetime2(7)                        NULL,
  update_by           bigint                              NULL,
  update_time         datetime2(7)                        NULL,
  remark              nvarchar(255)                       NULL,
  CONSTRAINT PK__submission__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
      WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
      ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('submission_testcase', 'U') IS NOT NULL
  DROP TABLE submission_testcase;
GO
CREATE TABLE submission_testcase
(
  id                  bigint                              NOT NULL,
  tenant_id           nvarchar(20)    DEFAULT ('000000')  NULL,
  submission_id       bigint                              NOT NULL,
  testcase_id         bigint                              NOT NULL,
  actual_output       nvarchar(255)                       NOT NULL,
  is_correct          bit                                 NOT NULL,
  executed_at         datetime2(7)                        NOT NULL,
  create_dept         bigint                              NULL,
  create_by           bigint                              NULL,
  create_time         datetime2(7)                        NULL,
  update_by           bigint                              NULL,
  update_time         datetime2(7)                        NULL,
  remark              nvarchar(255)                       NULL,
  CONSTRAINT PK__submission_testcase__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
      WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
      ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('exam_result', 'U') IS NOT NULL
  DROP TABLE exam_result;
GO
CREATE TABLE exam_result
(
  id                  bigint                              NOT NULL,
  tenant_id           nvarchar(20)    DEFAULT ('000000')  NULL,
  exam_id             bigint                              NOT NULL,
  user_id             bigint                              NOT NULL,
  total_score         tinyint                             NOT NULL,
  completed_at        datetime2(7)                        NOT NULL,
  create_dept         bigint                              NULL,
  create_by           bigint                              NULL,
  create_time         datetime2(7)                        NULL,
  update_by           bigint                              NULL,
  update_time         datetime2(7)                        NULL,
  remark              nvarchar(255)                       NULL,
  CONSTRAINT PK__seat__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
      WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
      ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('exam_result_detail', 'U') IS NOT NULL
  DROP TABLE exam_result_detail;
GO
CREATE TABLE exam_result_detail
(
  id                  bigint                              NOT NULL,
  tenant_id           nvarchar(20)    DEFAULT ('000000')  NULL,
  exam_id             bigint                              NOT NULL,
  exam_result_id      bigint                              NOT NULL,
  problem_id          bigint                              NOT NULL,
  score               tinyint                             NOT NULL,
  completed_time      varchar(5)                          NOT NULL,
  create_dept         bigint                              NULL,
  create_by           bigint                              NULL,
  create_time         datetime2(7)                        NULL,
  update_by           bigint                              NULL,
  update_time         datetime2(7)                        NULL,
  remark              nvarchar(255)                       NULL,
  CONSTRAINT PK__exam_result_detail__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
      WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
      ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('slide', 'U') IS NOT NULL
  DROP TABLE slide;
GO
CREATE TABLE slide
(
	id                  bigint                              NOT NULL,
  tenant_id           nvarchar(20)  DEFAULT ('000000')    NULL,
	slide_url	nvarchar(255)                       NULL,
	create_dept         bigint                              NULL,
  create_by           bigint                              NULL,
  create_time         datetime2(7)                        NULL,
  update_by           bigint                              NULL,
  update_time         datetime2(7)                        NULL,
  remark              nvarchar(500)                       NULL,
	CONSTRAINT PK__slide__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO
INSERT slide VALUES (1, N'000000', N'https://starlight.vn/Areas/Admin/Content/Fileuploads/images/Slider/hai.jpg', 103, 1, getdate(), NULL, NULL, 'TEST')
GO 
INSERT slide VALUES (2, N'000000', N'https://starlight.vn/Areas/Admin/Content/Fileuploads/images/Slider/z4831004558276_212eb4b5be997c49f8cb6bde9f02bab2.jpg', 103, 1, getdate(), NULL, NULL, 'TEST')
GO
INSERT slide VALUES (3, N'000000', N'https://starlight.vn/Areas/Admin/Content/Fileuploads/images/Slider/z4779781711597_672fa1fd6d3ec4549854486393104032(1).jpg', 103, 1, getdate(), NULL, NULL, 'TEST')
GO
INSERT slide VALUES (4, N'000000', N'https://starlight.vn/Areas/Admin/Content/Fileuploads/images/Slider/nguoi%20vo%20cuoi%20cung.jpg', 103, 1, getdate(), NULL, NULL, 'TEST')
GO

IF OBJECT_ID('sys_oper_log', 'U') IS NOT NULL
  DROP TABLE sys_oper_log;
GO
CREATE TABLE sys_oper_log
(
    oper_id        bigint                       NOT NULL,
    tenant_id      nvarchar(20)   DEFAULT ('000000') NULL,
    title          nvarchar(255)   DEFAULT ''    NULL,
    business_type  int            DEFAULT ((0)) NULL,
    method         nvarchar(100)  DEFAULT ''    NULL,
    request_method nvarchar(10)   DEFAULT ''    NULL,
    operator_type  int            DEFAULT ((0)) NULL,
    oper_name      nvarchar(255)   DEFAULT ''    NULL,
    dept_name      nvarchar(255)   DEFAULT ''    NULL,
    oper_url       nvarchar(255)  DEFAULT ''    NULL,
    oper_ip        nvarchar(128)  DEFAULT ''    NULL,
    oper_location  nvarchar(255)  DEFAULT ''    NULL,
    oper_param     nvarchar(2000) DEFAULT ''    NULL,
    json_result    nvarchar(2000) DEFAULT ''    NULL,
    system_type    nvarchar(30)   DEFAULT ('system')    NOT NULL,
    status         int            DEFAULT ((0)) NULL,
    error_msg      nvarchar(2000) DEFAULT ''    NULL,
    oper_time      datetime2(7)                 NULL,
    cost_time      bigint         DEFAULT ((0)) NULL,
    CONSTRAINT PK__sys_oper__34723BF9BD954573 PRIMARY KEY CLUSTERED (oper_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX idx_sys_oper_log_bt ON sys_oper_log (business_type)
GO
CREATE NONCLUSTERED INDEX idx_sys_oper_log_s ON sys_oper_log (status)
GO
CREATE NONCLUSTERED INDEX idx_sys_oper_log_ot ON sys_oper_log (oper_time)
GO

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(1,
'Longest Palindromic Substring',
 'Given a string s, return the longest palindromic substring in s.',
 2,
 SYSDATETIME());

 INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(1, 1, '"babad"', '"bab"', NULL, 'The longest palindromic substring is "bab" or "aba" (both are valid).', 0, SYSDATETIME()),
(2, 1, '"cbbd"', '"bb"', NULL, 'The longest palindromic substring is "bb".', 0, SYSDATETIME()),
(3, 1, '"a"', '"a"', NULL, 'Single character is a palindrome itself, so the output is "a".', 0, SYSDATETIME()),
(4, 1, '"ac"', '"a"', NULL, 'Single character substrings are palindromes, so the output is either "a" or "c".', 0, SYSDATETIME()),
(5, 1, '"forgeeksskeegfor"', '"geeksskeeg"', NULL, 'The longest palindromic substring is "geeksskeeg".', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(2,
'Zigzag Conversion',
 'Given a string s and a number of rows, write the code to convert the string in a zigzag pattern on the given number of rows, and then read it line by line.',
 2,
 SYSDATETIME());

 INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(6, 2, '"PAYPALISHIRING", 3', '"PAHNAPLSIIGYIR"', NULL, 'The string is written in zigzag pattern with 3 rows and then read line by line.', 0, SYSDATETIME()),
(7, 2, '"PAYPALISHIRING", 4', '"PINALSIGYAHRPI"', NULL, 'The string is written in zigzag pattern with 4 rows and then read line by line.', 0, SYSDATETIME()),
(8, 2, '"A", 1', '"A"', NULL, 'When there is only one row, the output is the same as the input.', 0, SYSDATETIME()),
(9, 2, '"HELLO", 2', '"HLOEL"', NULL, 'With 2 rows, the zigzag pattern forms "HLOEL" after reading line by line.', 0, SYSDATETIME()),
(10, 2, '"ABCDEFGHIJK", 5', '"AIJBHCGDFEK"', NULL, 'With 5 rows, the zigzag pattern forms "AIJBHCGDFEK" after reading line by line.', 0, SYSDATETIME());


INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(3,
'Reverse Integer',
 'Given a signed 32-bit integer x, return x with its digits reversed. If reversing x causes the value to go outside the signed 32-bit integer range [-231, 231 - 1], then return 0.',
 2,
 SYSDATETIME());

 INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(11, 3, '123', '321', NULL, 'Reversing 123 gives 321.', 0, SYSDATETIME()),
(12, 3, '-123', '-321', NULL, 'Reversing -123 gives -321.', 0, SYSDATETIME()),
(13, 3, '120', '21', NULL, 'Reversing 120 gives 21. Leading zero is discarded.', 0, SYSDATETIME()),
(14, 3, '0', '0', NULL, 'Reversing 0 gives 0. Edge case.', 0, SYSDATETIME()),
(15, 3, '1534236469', '0', NULL, 'Reversing 1534236469 exceeds the 32-bit integer range, so the output is 0.', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(4,
'String to Integer (myAtoi)',
 'Implement the myAtoi(string s) function, which converts a string to a 32-bit signed integer.',
 2,
 SYSDATETIME());

INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(16, 4, '42', '42', '', 'Input is a simple positive number.', 0, SYSDATETIME()),
(17, 4, '   -042', '-42', '', 'Input includes leading whitespace and a negative sign with leading zeros.', 0, SYSDATETIME()),
(18, 4, '1337c0d3', '1337', '', 'Digits are followed by non-numeric characters, so the number stops at the first non-digit character.', 0, SYSDATETIME()),
(19, 4, '0-1', '0', '', 'Input contains a valid number followed by non-digit characters.', 0, SYSDATETIME()),
(20, 4, 'words and 987', '0', '', 'Input starts with non-digit characters, so the result is 0.', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(5,
'Palindrome Number',
 'Given an integer x, return true if x is a palindrome, and false otherwise.',
 1,
 SYSDATETIME());

INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(21, 5, '121', 'true', NULL, '121 reads as 121 from left to right and from right to left.', 0, SYSDATETIME()),
(22, 5, '-121', 'false', NULL, 'From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.', 0, SYSDATETIME()),
(23, 5, '10', 'false', NULL, 'Reads as "01" from right to left. Therefore it is not a palindrome.', 0, SYSDATETIME()),
(24, 5, '0', 'true', NULL, '0 is a palindrome.', 0, SYSDATETIME()),
(25, 5, '12321', 'true', NULL, '12321 reads the same forwards and backwards.', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(6,
'Regular Expression Matching',
 'Given an input string s and a pattern p, implement regular expression matching with support for "." and "*" where: "." Matches any single character. "*" Matches zero or more of the preceding element. The matching should cover the entire input string (not partial).',
 3,
 SYSDATETIME());

INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(26, 6, '{"s": "aa", "p": "a"}', 'false', NULL, 'Pattern "a" does not match the entire string "aa".', 0, SYSDATETIME()),
(27, 6, '{"s": "aa", "p": "a*"}', 'true', NULL, 'Pattern "a*" matches "aa" as "*" allows for zero or more "a"s.', 0, SYSDATETIME()),
(28, 6, '{"s": "ab", "p": ".*"}', 'true', NULL, 'Pattern ".*" matches any sequence of characters, including "ab".', 0, SYSDATETIME()),
(29, 6, '{"s": "aab", "p": "c*a*b"}', 'true', NULL, 'Pattern "c*a*b" matches "aab" as "c*" matches zero "c"s, and "a*" matches "aa".', 0, SYSDATETIME()),
(30, 6, '{"s": "mississippi", "p": "mis*is*p*."}', 'false', NULL, 'Pattern does not match "mississippi" due to incorrect placement of "p*" in the pattern.', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(7,
'Container With Most Water',
 'You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]). Find two lines that together with the x-axis form a container, such that the container contains the most water. Return the maximum amount of water a container can store. Notice that you may not slant the container.',
 2,
 SYSDATETIME());

INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(31, 7, '[1,8,6,2,5,4,8,3,7]', '49', NULL, 'Max area is formed by lines at index 1 and 8 with heights 8 and 7.', 0, SYSDATETIME()),
(32, 7, '[1,1]', '1', NULL, 'Only two lines with minimum height 1, so max area is 1.', 0, SYSDATETIME()),
(33, 7, '[4,3,2,1,4]', '16', NULL, 'Max area is formed by lines at index 0 and 4 with heights 4 and 4.', 0, SYSDATETIME()),
(34, 7, '[1,2,1]', '2', NULL, 'Max area is formed by lines at index 0 and 2 with heights 1 and 1.', 0, SYSDATETIME()),
(35, 7, '[1,3,2,5,25,24,5]', '24', NULL, 'Max area is formed by lines at index 3 and 6 with heights 5 and 5.', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(8,
'Integer to Roman',
 'Given an integer, convert it to a Roman numeral. The conversion is based on the Roman numeral system with specific rules for subtractive notation and placement of symbols.',
 2,
 SYSDATETIME());

INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(36, 8, '3749', 'MMMDCCXLIX', NULL, '3000 = MMM, 700 = DCC, 40 = XL, 9 = IX.', 0, SYSDATETIME()),
(37, 8, '58', 'LVIII', NULL, '50 = L, 8 = VIII.', 0, SYSDATETIME()),
(38, 8, '1994', 'MCMXCIV', NULL, '1000 = M, 900 = CM, 90 = XC, 4 = IV.', 0, SYSDATETIME()),
(39, 8, '944', 'CMXLIV', NULL, '900 = CM, 40 = XL, 4 = IV.', 0, SYSDATETIME()),
(40, 8, '1023', 'MXXIII', NULL, '1000 = M, 20 = XX, 3 = III.', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(9,
'Roman to Integer',
 'Given a Roman numeral, convert it to an integer. Roman numerals are formed using the following symbols: I, V, X, L, C, D, M. Some numerals use subtraction notation (e.g., IV for 4, IX for 9). The valid range for Roman numerals in this problem is [1, 3999].',
 2,
 SYSDATETIME());

INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(41, 9, '"III"', '3', NULL, 'III = 3.', 0, SYSDATETIME()),
(42, 9, '"LVIII"', '58', NULL, 'L = 50, V = 5, III = 3. So, 50 + 5 + 3 = 58.', 0, SYSDATETIME()),
(43, 9, '"MCMXCIV"', '1994', NULL, 'M = 1000, CM = 900, XC = 90, IV = 4. So, 1000 + 900 + 90 + 4 = 1994.', 0, SYSDATETIME()),
(44, 9, '"IX"', '9', NULL, 'IX = 9 (I comes before X, subtract 1 from 10).', 0, SYSDATETIME()),
(45, 9, '"CDXLIV"', '444', NULL, 'CD = 400, XL = 40, IV = 4. So, 400 + 40 + 4 = 444.', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(10,
'Longest Common Prefix',
 'Write a function to find the longest common prefix string amongst an array of strings. If there is no common prefix, return an empty string.',
 2,
 SYSDATETIME());

INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(46, 10, '["flower", "flow", "flight"]', '"fl"', NULL, 'The longest common prefix between "flower", "flow", and "flight" is "fl".', 0, SYSDATETIME()),
(47, 10, '["dog", "racecar", "car"]', '""', NULL, 'There is no common prefix among the input strings, so the output is an empty string.', 0, SYSDATETIME()),
(48, 10, '["apple", "apricot", "ape"]', '"ap"', NULL, 'The longest common prefix between "apple", "apricot", and "ape" is "ap".', 0, SYSDATETIME()),
(49, 10, '["", "flower", "flow"]', '""', NULL, 'The first string is empty, so there is no common prefix.', 0, SYSDATETIME()),
(50, 10, '["interview", "interest", "internal"]', '"inte"', NULL, 'The longest common prefix between "interview", "interest", and "internal" is "inte".', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(11,
'3Sum',
'Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0. Notice that the solution set must not contain duplicate triplets.',
2,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(51, 11, '[-1, 0, 1, 2, -1, -4]', '[[-1, -1, 2], [-1, 0, 1]]', NULL, 'The triplets that sum up to zero are [-1, -1, 2] and [-1, 0, 1].', 0, SYSDATETIME()),
(52, 11, '[0, 1, 1]', '[]', NULL, 'There is no triplet that sums up to zero.', 0, SYSDATETIME()),
(53, 11, '[0, 0, 0]', '[[0, 0, 0]]', NULL, 'The only triplet that sums up to zero is [0, 0, 0].', 0, SYSDATETIME()),
(54, 11, '[-4, -1, -1, 0, 1, 2]', '[[-1, 0, 1], [-1, -1, 2]]', NULL, 'The triplets that sum up to zero are [-1, 0, 1] and [-1, -1, 2].', 0, SYSDATETIME()),
(55, 11, '[-1, 0, 1, 1]', '[[-1, 0, 1]]', NULL, 'The only distinct triplet that sums up to zero is [-1, 0, 1].', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(12,
'3Sum Closest',
'Given an integer array nums of length n and an integer target, find three integers in nums such that the sum is closest to target. Return the sum of the three integers. You may assume that each input would have exactly one solution.',
2,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(56, 12, '[-1, 2, 1, -4]', '2', NULL, 'The sum that is closest to the target is 2. (-1 + 2 + 1 = 2)', 0, SYSDATETIME()),
(57, 12, '[0, 0, 0]', '0', NULL, 'The sum that is closest to the target is 0. (0 + 0 + 0 = 0)', 0, SYSDATETIME()),
(58, 12, '[-1, 2, 3, -4, 5]', '7', NULL, 'The sum that is closest to the target is 7. (2 + 3 + (-4) + 5 = 7)', 0, SYSDATETIME()),
(59, 12, '[-1, -1, -1, 2]', '0', NULL, 'The sum that is closest to the target is 0. (-1 + (-1) + 2 = 0)', 0, SYSDATETIME()),
(60, 12, '[1, 1, 1, 1]', '3', NULL, 'The sum that is closest to the target is 3. (1 + 1 + 1 = 3)', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(13,
'Letter Combinations of a Phone Number',
'Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.',
2,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(61, 13, '"23"', '["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]', NULL, 'The digit 2 maps to "a", "b", "c" and the digit 3 maps to "d", "e", "f". All combinations of these letters are returned.', 0, SYSDATETIME()),
(62, 13, '""', '[]', NULL, 'An empty string returns an empty list because there are no digits to map to letters.', 0, SYSDATETIME()),
(63, 13, '"2"', '["a", "b", "c"]', NULL, 'The digit 2 maps to "a", "b", "c".', 0, SYSDATETIME()),
(64, 13, '"79"', '["pq", "pr", "ps", "qt", "qu", "qv", "rt", "ru", "rv", "st", "su", "sv"]', NULL, 'The digit 7 maps to "p", "q", "r", "s" and the digit 9 maps to "w", "x", "y", "z".', 0, SYSDATETIME()),
(65, 13, '"35"', '["df", "dg", "dh", "ef", "eg", "eh", "ff", "fg", "fh"]', NULL, 'The digit 3 maps to "d", "e", "f" and the digit 5 maps to "j", "k", "l".', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(14,
'4Sum',
'Given an array nums of n integers, return an array of all the unique quadruplets [nums[a], nums[b], nums[c], nums[d]] such that: 0 <= a, b, c, d < n, a, b, c, and d are distinct, nums[a] + nums[b] + nums[c] + nums[d] == target. You may return the answer in any order.',
3,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(66, 14, '[1, 0, -1, 0, -2, 2]', '[[ -2, -1, 1, 2 ], [ -2, 0, 0, 2 ], [ -1, 0, 0, 1 ]]', NULL, 'The quadruplets that sum to 0 are: [-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1].', 0, SYSDATETIME()),
(67, 14, '[2, 2, 2, 2, 2]', '[[2, 2, 2, 2]]', NULL, 'The only valid quadruplet that sums to 8 is [2, 2, 2, 2].', 0, SYSDATETIME()),
(68, 14, '[-1, 0, 1, 2, -1, -4]', '[[-1, 0, 1, 2]]', NULL, 'The only valid quadruplet that sums to 2 is [-1, 0, 1, 2].', 0, SYSDATETIME()),
(69, 14, '[1, 2, 3, 4, 5, 6]', '[[1, 2, 3, 4], [1, 2, 3, 5], [1, 2, 4, 5], [1, 3, 4, 5], [2, 3, 4, 5]]', NULL, 'The possible quadruplets that sum to 10 are: [1, 2, 3, 4], [1, 2, 3, 5], [1, 2, 4, 5], [1, 3, 4, 5], [2, 3, 4, 5].', 0, SYSDATETIME()),
(70, 14, '[-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5]', '[[ -5, 0, 3, 2 ], [ -5, 1, 3, 1 ], [ -4, 0, 4, 0 ], ...]', NULL, 'There are multiple valid quadruplets that sum to 0, such as [-5, 0, 3, 2], [-4, -1, 3, 2], and others.', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(15,
'Remove Nth Node From End of List',
'Given the head of a linked list, remove the nth node from the end of the list and return its head.',
2,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(71, 15, '[1,2,3,4,5], 2', '[1, 2, 3, 5]', NULL, 'Removing the 2nd node from the end gives the list: [1, 2, 3, 5].', 0, SYSDATETIME()),
(72, 15, '[1], 1', '[]', NULL, 'Removing the 1st node from the end gives an empty list: [].', 0, SYSDATETIME()),
(73, 15, '[1, 2], 1', '[1]', NULL, 'Removing the 1st node from the end gives the list: [1].', 0, SYSDATETIME()),
(74, 15, '[10, 20, 30, 40, 50], 3', '[10, 20, 40, 50]', NULL, 'Removing the 3rd node from the end gives the list: [10, 20, 40, 50].', 0, SYSDATETIME()),
(75, 15, '[5, 10, 15, 20, 25], 4', '[5, 10, 15, 25]', NULL, 'Removing the 4th node from the end gives the list: [5, 10, 15, 25].', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(16,
'Valid Parentheses',
'Given a string s containing just the characters (''('', '')'', ''{'', ''}'', ''['', '']'')'', determine if the input string is valid. An input string is valid if: Open brackets must be closed by the same type of brackets. Open brackets must be closed in the correct order. Every close bracket has a corresponding open bracket of the same type.',
2,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(76, 16, '()',
'true', NULL, 'The string contains only one pair of valid parentheses: ().', 0, SYSDATETIME()),
(77, 16, '()[]{}',
'true', NULL, 'The string contains valid pairs of parentheses: (), [], and {}.', 0, SYSDATETIME()),
(78, 16, '(]',
'false', NULL, 'The parentheses are mismatched; "(" is not closed by a "]".', 0, SYSDATETIME()),
(79, 16, '([])',
'true', NULL, 'The parentheses are correctly matched and nested: (), [].', 0, SYSDATETIME()),
(80, 16, '{[()]}',
'true', NULL, 'The string contains valid and properly nested parentheses: {}, [], ().', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(17,
'Merge Two Sorted Lists',
'You are given the heads of two sorted linked lists list1 and list2. Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists. Return the head of the merged linked list.',
2,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(81, 17, '[1,2,4], [1,3,4]',
'[1,1,2,3,4,4]', NULL, 'Both lists are merged and sorted in non-decreasing order.', 0, SYSDATETIME()),
(82, 17, '[], []',
'[]', NULL, 'Both lists are empty, so the merged list is also empty.', 0, SYSDATETIME()),
(83, 17, '[], [0]',
'[0]', NULL, 'The first list is empty, and the second list contains a single node.', 0, SYSDATETIME()),
(84, 17, '[-1,0,3], [-2,1,4]',
'[-2,-1,0,1,3,4]', NULL, 'The two lists are merged and sorted in non-decreasing order.', 0, SYSDATETIME()),
(85, 17, '[-5,0,2], [-4,1,3]',
'[-5,-4,0,1,2,3]', NULL, 'The two lists are merged and sorted in non-decreasing order.', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(18,
'Generate Parentheses',
'Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.',
2,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(86, 18, '3',
'["((()))", "(()())", "(())()", "()(())", "()()()"]', NULL, 'With 3 pairs of parentheses, the function generates all valid combinations.', 0, SYSDATETIME()),
(87, 18, '1',
'["()"]', NULL, 'With just 1 pair, the only valid combination is "()".', 0, SYSDATETIME()),
(88, 18, '2',
'["(())", "()()"]', NULL, 'With 2 pairs of parentheses, the valid combinations are "(())" and "()()".', 0, SYSDATETIME()),
(89, 18, '4',
'["(((())))", "((()()))", "((())())", "((()))()", "(()(()))", "(()()())", "(()())()", "(())(())", "(())()()", "()((()))", "()(()())", "()()(())", "()()()()"]', NULL, 'The function generates all well-formed combinations of parentheses for 4 pairs.', 0, SYSDATETIME()),
(90, 18, '5',
'["((((()))))", "(((()())))", "(((()()))())", "(((()())))()", "((())(())))", "((())(())())", "((())(())()()", "(())(())(())", "(())()()()", "()(())(())", "()(())()()", "()()()(())"]', NULL, 'The function generates all well-formed combinations of parentheses for 5 pairs.', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(19,
'Merge k Sorted Lists',
'You are given an array of k linked-lists, each linked-list is sorted in ascending order. Merge all the linked-lists into one sorted linked-list and return it.',
2,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(91, 19, '[[1,4,5],[1,3,4],[2,6]]',
'[1,1,2,3,4,4,5,6]', NULL, 'Merging the 3 sorted lists into one sorted list.', 0, SYSDATETIME()),
(92, 19, '[]',
'[]', NULL, 'No lists to merge, so the result is an empty list.', 0, SYSDATETIME()),
(93, 19, '[[]]',
'[]', NULL, 'The single list is empty, so the result is an empty list.', 0, SYSDATETIME()),
(94, 19, '[[1,2,3], [4,5,6], [7,8,9]]',
'[1,2,3,4,5,6,7,8,9]', NULL, 'We merge the 3 sorted lists into one sorted list, keeping the order intact.', 0, SYSDATETIME()),
(95, 19, '[[-1,0,1], [-2,0,2], [3,4,5]]',
'[-2,-1,0,0,1,2,3,4,5]', NULL, 'Merging the 3 sorted lists results in the sorted order of elements.', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(20,
'Swap Nodes in Pairs',
'Given a linked list, swap every two adjacent nodes and return its head. You must solve the problem without modifying the values in the list nodes (i.e., only nodes themselves may be changed.)',
2,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(96, 20, '[1,2,3,4]',
'[2,1,4,3]', NULL, 'Swapping each pair of adjacent nodes results in the list [2,1,4,3].', 0, SYSDATETIME()),
(97, 20, '[]',
'[]', NULL, 'The linked list is empty, so the result is an empty list.', 0, SYSDATETIME()),
(98, 20, '[1]',
'[1]', NULL, 'Only one node exists, so there are no adjacent nodes to swap.', 0, SYSDATETIME()),
(99, 20, '[1,2,3]',
'[2,1,3]', NULL, 'Only the first two nodes (1 and 2) are swapped, leaving the last node unchanged.', 0, SYSDATETIME()),
(100, 20, '[10,20,30,40,50]',
'[20,10,40,30,50]', NULL, 'The adjacent pairs (10,20), (30,40) are swapped, while the last node (50) remains the same.', 0, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, create_time)
VALUES
(21,
'Reverse Nodes in k-Group',
'Given the head of a linked list, reverse the nodes of the list k at a time, and return the modified list. k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is. You may not alter the values in the list nodes, only nodes themselves may be changed.',
3,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, input_data, expected_output, illustration, explanation, is_hidden, create_time)
VALUES
(101, 21, '[1,2,3,4,5], 2',
'[2,1,4,3,5]', NULL, 'The first two nodes (1, 2) are reversed, then the next two nodes (3, 4), and the last node (5) remains as it is.', 0, SYSDATETIME()),
(102, 21, '[1,2,3,4,5], 3',
'[3,2,1,4,5]', NULL, 'The first three nodes (1, 2, 3) are reversed, leaving the rest (4, 5) untouched.', 0, SYSDATETIME()),
(103, 21, '[1,2,3,4,5], 5',
'[5,4,3,2,1]', NULL, 'Since k equals the length of the list, all nodes are reversed in one go.', 0, SYSDATETIME()),
(104, 21, '[1,2,3,4,5], 1',
'[1,2,3,4,5]', NULL, 'Since k is 1, no nodes are reversed, and the list remains the same.', 0, SYSDATETIME()),
(105, 21, '[1,2,3,4,5,6], 4',
'[4,3,2,1,5,6]', NULL, 'The first four nodes (1, 2, 3, 4) are reversed, and the remaining nodes (5, 6) are left unchanged.', 0, SYSDATETIME());

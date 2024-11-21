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

INSERT sys_user VALUES (1, N'000000', 0, 100, N'admin', N'Administrator', N'sys_user', N'admin@gmail.com', N'0987654321', N'1', NULL, N'$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', N'0', N'0', N'127.0.0.1', getdate(), 103, 1, getdate(), NULL, NULL, N'administrator', N'system')
GO
INSERT sys_user VALUES (2, N'000000', 1, 103, N'hoald1', N'Lê Đình Hòa', N'sys_user', N'hoald1@gmail.com', N'0999999999', N'1', NULL, N'$2a$10$ASuKduzRhpJdv2be3MQWi.PIxMeNMokIfKJLWc2cP3k0sVhXEtb0K', N'0', N'0', N'127.0.0.1', getdate(), 103, 1, getdate(), NULL, NULL, N'', N'system')
GO
INSERT sys_user VALUES (3, N'000000', 2, 103, N'hoald2', N'Lê Đình Hòa', N'sys_user', N'hoald2@gmail.com', N'0999999999', N'1', NULL, N'$2a$10$ASuKduzRhpJdv2be3MQWi.PIxMeNMokIfKJLWc2cP3k0sVhXEtb0K', N'0', N'0', N'127.0.0.1', getdate(), 103, 1, getdate(), NULL, NULL, N'', N'system')
GO
INSERT sys_user VALUES (4, N'000000', 3, 103, N'hoald3', N'Lê Đình Hòa', N'sys_user', N'hoald3@gmail.com', N'0999999999', N'1', NULL, N'$2a$10$ASuKduzRhpJdv2be3MQWi.PIxMeNMokIfKJLWc2cP3k0sVhXEtb0K', N'0', N'0', N'127.0.0.1', getdate(), 103, 1, getdate(), NULL, NULL, N'', N'system')
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
  is_hidden           tinyint(1)                          NOT NULL,
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
  status              tinyint(1)                          NOT NULL,
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

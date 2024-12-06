<template>
  <Layout3
    ref="layout"
    v-loading="layoutLoading"
    :loadingLeft="problemLoading"
    :loadingRight="testcaseLoading"
    :queryParamsLeft="problemQueryParams"
    :queryParamsRight="testcaseQueryParams"
    :totalLeft="problemTotal"
    :rowDataLeft="problemList"
    v-model:rowDataRight="testcaseList"
    :columnSettingLeft="problemColumns"
    :columnSettingRight="testcaseColumns"
    :rowKeyLeft="problemRowKey"
    :rowKeyRight="testcaseRowKey"
    :checkboxColLeft="true"
    :checkboxColRight="true"
    @onSelectedRowsLeft="handleSelectProblemRows"
    @onSelectedRowsRight="handleSelectContainerRows"
    @onSelectCellLeft="handleSelectCellProblem"
    @onSearchLeft="handleQuery"
    @onResetLeft="resetQuery"
    @onPagingLeft="getProblemList"
    @onPagingRight="getTestcaseList"
  >
    <template v-slot:search-input>
      <el-form-item :label="$t('problemManagement.searchInput.titleLb')" class="form-item-search">
        <el-input
          v-model="problemQueryParams.title"
          :placeholder="$t('problemManagement.searchInput.titlePh')"
          clearable
          style="width: 120px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('problemManagement.searchInput.difficultyLb')" prop="difficulty" class="form-item-search">
        <el-select v-model="problemQueryParams.difficulty" :placeholder="$t('problemManagement.searchInput.difficultyPh')" clearable style="width: 100px">
          <el-option v-for="dict in level" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
    </template>
    <template v-slot:header-button-left>
      <IrButton
        colorStyle="blue"
        type="primary"
        :title="$t('problemManagement.headerButton.addTt')"
        v-hasPermi="['portCustomer:problemManagement:add']"
        leftIcon="plus"
        @onClick="handleProblemAdd"
      />
      <IrButton
        colorStyle="gray"
        type="secondary"
        :disabledFlag="problemIds.length !== 1 ? true : false"
        :title="$t('problemManagement.headerButton.editTt')"
        leftIcon="edit"
        v-hasPermi="['portCustomer:problemManagement:edit']"
        @onClick="handleProblemUpdate"
      />
      <IrButton
        colorStyle="red"
        type="secondary"
        :disabledFlag="!!!problemIds.length"
        :title="$t('problemManagement.headerButton.deleteTt')"
        leftIcon="delete"
        v-hasPermi="['portCustomer:problemManagement:edit']"
        @onClick="handleDeleteProblem"
      />
    </template>
    <template v-slot:header-button-right>
      <IrButton
        colorStyle="green"
        type="secondary"
        :title="$t('problemManagement.headerButton.saveTt')"
        leftIcon="save"
        @onClick="handleTestcaseUpdate"
        :disabledFlag="!!!testcaseIds.length"
      />
      <IrButton
        colorStyle="gray"
        type="secondary"
        :disabledFlag="testcaseIds.length !== 1 ? true : false"
        :title="$t('problemManagement.headerButton.editTt')"
        leftIcon="edit"
        v-hasPermi="['portCustomer:problemManagement:edit']"
        @onClick="handleTestcaseUpdate2"
      />
    </template>
    <template v-slot:dialog>
      <IrDialog :dialog="problemDialog">
        <template v-slot:body>
          <el-form
            ref="problemFormRef"
            :rules="problemFormRules"
            :model="problemForm"
            label-width="150px"
            class="common-form"
            v-loading="layoutProblemFormLoading"
          >
            <el-form-item label="Tên bài toán" prop="title" class="form-item-row">
              <el-input v-model="problemForm.title" placeholder="Nhập" />
            </el-form-item>
            <el-form-item label="Mô tả" prop="description" class="form-item-row">
              <el-input v-model="problemForm.description" placeholder="Nhập" />
            </el-form-item>
            <el-form-item label="Độ khó" class="form-item-row">
              <el-select v-model="problemForm.difficulty" placeholder="Độ khó">
                <el-option v-for="dict in level" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="Ghi chú" prop="remark" class="form-item-row">
              <el-input v-model="problemForm.remark" type="textarea" placeholder="Nhập" />
            </el-form-item>
          </el-form>
        </template>
        <template v-slot:footer>
          <IrButton colorStyle="gray" type="secondary" title="Đóng" :width="100" @onClick="cancel" />
          <IrButton colorStyle="blue" type="primary" :title="$t('user.dialog.footerSubmitTt')" :width="100" @onClick="submitProblemForm" />
        </template>
      </IrDialog>
      <IrDialog :dialog="testcaseDialog">
        <template v-slot:body>
          <el-form
            ref="testcaseFormRef"
            :rules="testcaseFormRules"
            :model="testcaseForm"
            label-width="150px"
            class="common-form"
            v-loading="layoutTestcaseFormLoading"
          >
            <el-form-item label="Ảnh minh họa" prop="illustration" class="form-item-row">
              <fileUpload
                ref="fileUploadRef"
                v-model:ossId="testcaseForm.ossId"
                v-model:url="testcaseForm.illustration"
                configKey="public_document"
                :required="false"
                :autoUpload="false"
                :fileSize="50"
                :limit="1"
                :loading="buttonLoading"
                @submitForm="submitTestcaseForm"
              />
            </el-form-item>
          </el-form>
        </template>
        <template v-slot:footer>
          <IrButton colorStyle="gray" type="secondary" title="Đóng" :width="100" @onClick="cancel2" />
          <IrButton colorStyle="blue" type="primary" :title="$t('user.dialog.footerSubmitTt')" :width="100" @onClick="submitTestcaseForm" />
        </template>
      </IrDialog>
    </template>
  </Layout3>
</template>
<script setup name="problem" lang="ts">
///////////////////////////////////////////////////////////////////////////////
// IMPORT SECTION
///////////////////////////////////////////////////////////////////////////////
// IMPORT COMPONENT
// IMPORT API
import { listProblem, getProblem, getTestcase, delProblem, addProblem, updateProblem, updateTestcase, listTestcase, updateTestcases } from '@/api/portCustomer/publicProblemManagement';
// IMPORT TYPE
import { ProblemVO, TestcaseVO, ProblemQuery, TestcaseQuery, ProblemForm, TestcaseForm } from '@/api/portCustomer/publicProblemManagement/types';
import { ElForm, FormRules } from 'element-plus';
// IMPORT GLOBAL TOOL (PROXY)
import { ComponentInternalInstance, reactive } from "vue";
const { proxy } = getCurrentInstance() as ComponentInternalInstance;
// IMPORT DICTIONARY
const { level } = toRefs<any>(proxy?.useDict("level"))
const { sys_show_hide } = toRefs<any>(proxy?.useDict("sys_show_hide"))
///////////////////////////////////////////////////////////////////////////////
// VARIABLE SECTION
///////////////////////////////////////////////////////////////////////////////
const buttonLoading = ref(false);
const problemId = ref<any>('');
const problemSelected = ref<any>({});
const layoutLoading = ref(false);
const layoutProblemFormLoading = ref(false);
const layoutTestcaseFormLoading = ref(false);
const fileUploadRef = ref();
const problemList = ref<ProblemVO[]>([]);
const problemIds = ref(<any>[]);
const problemLoading = ref(true);
const problemTotal = ref(0);
const problemRowKey = ref("id");
const problemColumns = ref<GridColumn[]>([
  { prop: "title", name: 'problemManagement.columns.titleLb', size: 220, readonly: true, align: 'left' },
  { prop: "description", name: 'problemManagement.columns.descriptionLb', sortable: true, size: 280, readonly: true, align: 'left' },
  { prop: "difficulty", name: 'problemManagement.columns.difficultyLb', sortable: true, size: 90, readonly: true, align: 'left', dictData: level  },
  { prop: "createTime", name: 'problemManagement.columns.createTimeLb', sortable: true, size: 160, readonly: true, formatter: 'datetime' },
  { prop: "remark", name: 'problemManagement.columns.remarkLb', sortable: true, size: 120, readonly: true, align: 'left' },
]);
const testcaseIds = ref(<any>[]);
const testcaseList = ref<any[]>([]);
const testcaseLoading = ref(false);
const testcaseRowKey = ref("id");
const testcaseColumns = ref<GridColumn[]>([
  { prop: "inputData", name: 'problemManagement.testcaseColumns.inputDataLb', sortable: true, size: 130, align: 'center' },
  { prop: "expectedOutput", name: 'problemManagement.testcaseColumns.expectedOutputLb', sortable: true, size: 150, align: 'left' },
  { prop: "explanation", name: 'problemManagement.testcaseColumns.explanationLb', sortable: true, size: 180, align: 'left' },
  { prop: "isHidden", name: 'problemManagement.testcaseColumns.isHiddenLb', sortable: true, size: 80, align: 'center', dictData: sys_show_hide },
  { prop: "illustration", name: 'problemManagement.testcaseColumns.illustrationLb', sortable: true, show: false, size: 140, align: 'left' },
  { prop: "remark", name: 'problemManagement.testcaseColumns.remarkLb', size: 120, align: 'left' }
]);
const problemQueryParams = reactive<ProblemQuery>({
  pageNum: 1,
  pageSize: 20,
  title: '',
  description: '',
  difficulty: '',
  remark: '',
  orderByColumn: 'createTime',
  isAsc: 'descending',
});
const testcaseQueryParams = reactive<TestcaseQuery>({
  problemId: undefined
});
const problemFormRef = ref(ElForm);
const initProblemFormData: ProblemForm = {
  id: undefined,
  remark: undefined
}
const problemDialog = reactive<DialogOption>({
  visible: false,
  title: '',
  width: '600px'
});
const problemForm = ref<ProblemForm>({...initProblemFormData});
const problemFormRules: FormRules = {
  title: [{required: true, trigger: "blur", message: "Tên bài toán không được trống", }],
  description: [{ required: true, trigger: "blur", message: "Mô tả bài toán không được trống" }],
  difficulty: [{ required: true, trigger: "blur", message: "Độ khó là bắt buộc" }]
};

const testcaseFormRef = ref(ElForm);
const initTestcaseFormData: TestcaseForm = {
  id: undefined,
  remark: undefined
}
const testcaseDialog = reactive<DialogOption>({
  visible: false,
  title: '',
  width: '600px'
});
const testcaseForm = ref<TestcaseForm>({...initTestcaseFormData});
const testcaseFormRules: FormRules = {
  problemId: [{required: true, trigger: "blur", message: "Bài toán là bắt buộc", }]
};
///////////////////////////////////////////////////////////////////////////////
// METHOD SECTION
///////////////////////////////////////////////////////////////////////////////
const handleSelectProblemRows = (selectedIds: { value: (string | number)[]; }) => {
  problemIds.value = selectedIds.value;
}
const handleSelectContainerRows = (selectedIds: { value: (string | number)[]; }) => {
  testcaseIds.value = selectedIds.value;
}
const handleSelectCellProblem = (modal: any) => {
  testcaseIds.value = [];
  resetTestcaseList();
  testcaseQueryParams.problemId = problemList.value[modal.rowIndex].id;
  problemSelected.value = problemList.value[modal.rowIndex];
  problemId.value = problemList.value[modal.rowIndex].id;
  getTestcaseList();
}
/** Query problem list */
const getProblemList = async () => {
  problemLoading.value = true;
  const res = await listProblem(problemQueryParams);
  problemList.value = res.rows;
  problemTotal.value = res.total;
  problemLoading.value = false;
  resetTestcaseList();
  if (problemList.value.length > 0) {
    testcaseQueryParams.problemId = problemList.value[0].id;
    problemSelected.value = problemList.value[0];
    problemId.value = problemList.value[0].id;
    getTestcaseList();
  }
}
/** Query problem detail list */
const getTestcaseList = async () => {
  testcaseLoading.value = true;
  const res = await listTestcase(testcaseQueryParams);
  testcaseList.value = res.rows;
  const diff = problemSelected.value.rowNumber - testcaseList.value.length;
  if (diff > 0) {
    for (let i = 0; i < diff; i++) {
      testcaseList.value.push({ key: i });
    }
  }
  for (let i = 0; i < testcaseList.value.length; i++) {
    if (testcaseList.value[i].id) {
      testcaseList.value[i].key = testcaseList.value[i].id;
    }
  }
  testcaseLoading.value = false;
}
/** Reset container list */
const resetTestcaseList = () => {
  problemSelected.value = {};
  problemId.value = '';
  testcaseList.value = [];
  problemIds.value = [];
  testcaseQueryParams.problemId = undefined;
}
/** Cancel button */
const cancel = () => {
  reset();
  problemDialog.visible = false;
}

const cancel2 = () => {
  testcaseDialog.visible = false;
}
/** Form reset */
const reset = () => {
  problemForm.value = {...initProblemFormData};
  problemFormRef.value.resetFields();
}
/** Search button action */
const handleQuery = () => {
  problemQueryParams.pageNum = 1;
  getProblemList();
}
/** Reset button action */
const resetQuery = () => {
  handleQuery();
}
/** Add button action */
const handleProblemAdd = () => {
  problemDialog.visible = true;
  problemDialog.title = 'Tạo bài toán';
  nextTick(() => {
    resetProblemForm();
  })
}
/** Edit button action */
const handleProblemUpdate = (row?: ProblemForm | ProblemVO) => {
  problemDialog.visible = true;
  problemDialog.title = 'Cập nhật thông tin bài toán';
  nextTick(async () => {
    resetProblemForm();
    const _id = problemIds.value[0]
    const res = await getProblem(_id);
    Object.assign(problemForm.value, res.data);
  })
}
/** Form reset */
const resetProblemForm = () => {
  problemForm.value = {...initProblemFormData};
  problemFormRef.value.resetFields();
}
/** Submit button */
const submitProblemForm = () => {
  problemFormRef.value.validate(async (valid: boolean) => {
    if (valid && (fileUploadRef.value ? fileUploadRef.value.submitFile() : true)) {
      layoutProblemFormLoading.value = true;
      if (problemForm.value.id) {
        await updateProblem(problemForm.value).finally(() => layoutProblemFormLoading.value = false);
      } else {
        await addProblem(problemForm.value).finally(() => layoutProblemFormLoading.value = false);
      }
      proxy?.$modal.msgSuccess('Lưu thành công');
      problemDialog.visible = false;
      await getProblemList();
    }
  });
}
/** Edit button action */
const handleTestcaseUpdate2 = (row?: TestcaseForm | TestcaseVO) => {
  testcaseDialog.visible = true;
  testcaseDialog.title = 'Cập nhật thông tin ảnh minh họa';
  nextTick(async () => {
    resetTestcaseForm();
    const _id = testcaseIds.value[0]
    const res = await getTestcase(_id);
    Object.assign(testcaseForm.value, res.data);
  })
}
/** Form reset */
const resetTestcaseForm = () => {
  testcaseForm.value = {...initTestcaseFormData};
  testcaseFormRef.value.resetFields();
}
/** Submit button */
const submitTestcaseForm = () => {
  testcaseFormRef.value.validate(async (valid: boolean) => {
    if (valid && (fileUploadRef.value ? fileUploadRef.value.submitFile() : true)) {
      layoutTestcaseFormLoading.value = true;
      if (testcaseForm.value.id) {
        await updateTestcase(testcaseForm.value).finally(() => layoutTestcaseFormLoading.value = false);
      }
      proxy?.$modal.msgSuccess('Lưu thành công');
      testcaseDialog.visible = false;
    }
  });
}
/** Save list testcase */
const handleTestcaseUpdate = async () => {
  layoutLoading.value = true;
  //let listTestcaseData = testcaseList.value.filter((testcase) => testcaseIds.value.includes(parseInt(testcase.id + '')))
  let listTestcaseData = testcaseList.value;
  await updateTestcases(listTestcaseData, problemSelected.value.id).finally(() => layoutLoading.value = false);
  getTestcaseList();
}

/** Validate delete */
const handleDeleteProblem = async () => {
  const ids = problemIds.value
  await proxy?.$modal.confirmDelete("Xác nhận xóa bài toán?").then(action => {
    layoutLoading.value = true;
    if (action === 'cancel') return;
    delProblem(ids).finally(() => layoutLoading.value = false);
  })
  getProblemList();
}
///////////////////////////////////////////////////////////////////////////////
// INIT SECTION
///////////////////////////////////////////////////////////////////////////////
onMounted(() => {
  getProblemList();
});
</script>

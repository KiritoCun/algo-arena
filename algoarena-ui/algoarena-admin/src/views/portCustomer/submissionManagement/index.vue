<template>
  <Layout3
    ref="layout"
    v-loading="layoutLoading"
    :loadingLeft="submissionLoading"
    :loadingRight="submissionTestcaseLoading"
    :queryParamsLeft="submissionQueryParams"
    :queryParamsRight="submissionTestcaseQueryParams"
    :totalLeft="submissionTotal"
    :rowDataLeft="submissionList"
    v-model:rowDataRight="submissionTestcaseList"
    :columnSettingLeft="submissionColumns"
    :columnSettingRight="submissionTestcaseColumns"
    :rowKeyLeft="submissionRowKey"
    :rowKeyRight="submissionTestcaseRowKey"
    :checkboxColLeft="true"
    :checkboxColRight="true"
    @onSelectedRowsLeft="handleSelectSubmissionRows"
    @onSelectedRowsRight="handleSelectContainerRows"
    @onSelectCellLeft="handleSelectCellSubmission"
    @onSearchLeft="handleQuery"
    @onResetLeft="resetQuery"
    @onPagingLeft="getSubmissionList"
    @onPagingRight="getSubmissionTestcaseList"
  >
    <template v-slot:search-input>
      <el-form-item :label="$t('submissionManagement.searchInput.titleLb')" class="form-item-search">
        <el-input
          v-model="submissionQueryParams.title"
          :placeholder="$t('submissionManagement.searchInput.titlePh')"
          clearable
          style="width: 120px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
    </template>
  </Layout3>
</template>
<script setup name="submission" lang="ts">
///////////////////////////////////////////////////////////////////////////////
// IMPORT SECTION
///////////////////////////////////////////////////////////////////////////////
// IMPORT COMPONENT
// IMPORT API
import { listSubmission, getSubmission, delSubmission, addSubmission, updateSubmission, listSubmissionTestcase, updateSubmissionTestcases } from '@/api/portCustomer/submissionManagement';
// IMPORT TYPE
import { SubmissionVO, SubmissionQuery, SubmissionTestcaseQuery, SubmissionForm } from '@/api/portCustomer/submissionManagement/types';
import { ElForm, FormRules } from 'element-plus';
// IMPORT GLOBAL TOOL (PROXY)
import { ComponentInternalInstance, reactive } from "vue";
const { proxy } = getCurrentInstance() as ComponentInternalInstance;
// IMPORT DICTIONARY
///////////////////////////////////////////////////////////////////////////////
// VARIABLE SECTION
///////////////////////////////////////////////////////////////////////////////
const submissionId = ref<any>('');
const submissionSelected = ref<any>({});
const layoutLoading = ref(false);
const layoutSubmissionFormLoading = ref(false);
const fileUploadRef = ref();
const submissionList = ref<SubmissionVO[]>([]);
const submissionIds = ref(<any>[]);
const submissionLoading = ref(true);
const submissionTotal = ref(0);
const submissionRowKey = ref("id");
const submissionColumns = ref<GridColumn[]>([
  { prop: "examName", name: 'submissionManagement.columns.examNameLb', size: 220, readonly: true, align: 'left' },
  { prop: "problemName", name: 'submissionManagement.columns.problemNameLb', sortable: true, size: 220, readonly: true, align: 'left' },
  { prop: "userName", name: 'submissionManagement.columns.userNameLb', sortable: true, size: 160, readonly: true, align: 'left' },
  { prop: "code", name: 'submissionManagement.columns.codeLb', sortable: true, size: 280, readonly: true, align: 'left' },
  { prop: "status", name: 'submissionManagement.columns.statusLb', sortable: true, size: 160, readonly: true, align: 'left' },
  { prop: "score", name: 'submissionManagement.columns.statusLb', sortable: true, size: 160, readonly: true, align: 'left' },
  { prop: "createdTime", name: 'submissionManagement.columns.createdTimeLb', sortable: true, size: 160, readonly: true, formatter: 'datetime' },
]);
const submissionTestcaseIds = ref(<any>[]);
const submissionTestcaseList = ref<any[]>([]);
const submissionTestcaseLoading = ref(false);
const submissionTestcaseRowKey = ref("id");
const submissionTestcaseColumns = ref<GridColumn[]>([
  { prop: "inputData", name: 'submissionManagement.submissionTestcaseColumns.inputDataLb', sortable: true, size: 130, align: 'center' },
  { prop: "expectedOutput", name: 'submissionManagement.submissionTestcaseColumns.expectedOutputLb', sortable: true, size: 150, align: 'left' },
  { prop: "actualOutput", name: 'submissionManagement.submissionTestcaseColumns.actualOutputLb', sortable: true, size: 150, align: 'left' },
  { prop: "isCorrect", name: 'submissionManagement.submissionTestcaseColumns.isCorrectLb', sortable: true, size: 120, align: 'left' },
  { prop: "executedTime", name: 'submissionManagement.submissionTestcaseColumns.executedTimeLb', sortable: true, size: 160, readonly: true, formatter: 'datetime' },
]);
const submissionQueryParams = reactive<SubmissionQuery>({
  pageNum: 1,
  pageSize: 20,
  title: '',
  description: '',
  difficulty: '',
  remark: '',
  orderByColumn: 'createTime',
  isAsc: 'descending',
});
const submissionTestcaseQueryParams = reactive<SubmissionTestcaseQuery>({
  submissionId: undefined
});
const submissionFormRef = ref(ElForm);
const initSubmissionFormData: SubmissionForm = {
  id: undefined,
  remark: undefined
}
const submissionDialog = reactive<DialogOption>({
  visible: false,
  title: '',
  width: '600px'
});
const submissionForm = ref<SubmissionForm>({...initSubmissionFormData});
const submissionFormRules: FormRules = {
  cinemaId: [{required: true, trigger: "blur", message: "Rạp chiếu không được trống", }],
  submissionName: [{ required: true, trigger: "blur", message: "Tên phòng chiếu không được trống" }]
};
///////////////////////////////////////////////////////////////////////////////
// METHOD SECTION
///////////////////////////////////////////////////////////////////////////////
const handleSelectSubmissionRows = (selectedIds: { value: (string | number)[]; }) => {
  submissionIds.value = selectedIds.value;
}
const handleSelectContainerRows = (selectedIds: { value: (string | number)[]; }) => {
  submissionTestcaseIds.value = selectedIds.value;
}
const handleSelectCellSubmission = (modal: any) => {
  submissionTestcaseIds.value = [];
  resetSubmissionTestcaseList();
  submissionTestcaseQueryParams.submissionId = submissionList.value[modal.rowIndex].id;
  submissionSelected.value = submissionList.value[modal.rowIndex];
  submissionId.value = submissionList.value[modal.rowIndex].id;
  getSubmissionTestcaseList();
}
/** Query submission list */
const getSubmissionList = async () => {
  submissionLoading.value = true;
  const res = await listSubmission(submissionQueryParams);
  submissionList.value = res.rows;
  submissionTotal.value = res.total;
  submissionLoading.value = false;
  resetSubmissionTestcaseList();
  if (submissionList.value.length > 0) {
    submissionTestcaseQueryParams.submissionId = submissionList.value[0].id;
    submissionSelected.value = submissionList.value[0];
    submissionId.value = submissionList.value[0].id;
    getSubmissionTestcaseList();
  }
}
/** Query submission detail list */
const getSubmissionTestcaseList = async () => {
  submissionTestcaseLoading.value = true;
  const res = await listSubmissionTestcase(submissionTestcaseQueryParams);
  submissionTestcaseList.value = res.rows;
  const diff = submissionSelected.value.rowNumber - submissionTestcaseList.value.length;
  if (diff > 0) {
    for (let i = 0; i < diff; i++) {
      submissionTestcaseList.value.push({ key: i });
    }
  }
  for (let i = 0; i < submissionTestcaseList.value.length; i++) {
    if (submissionTestcaseList.value[i].id) {
      submissionTestcaseList.value[i].key = submissionTestcaseList.value[i].id;
    }
  }
  submissionTestcaseLoading.value = false;
}
/** Reset container list */
const resetSubmissionTestcaseList = () => {
  submissionSelected.value = {};
  submissionId.value = '';
  submissionTestcaseList.value = [];
  submissionIds.value = [];
  submissionTestcaseQueryParams.submissionId = undefined;
}
/** Cancel button */
const cancel = () => {
  reset();
  submissionDialog.visible = false;
}
/** Form reset */
const reset = () => {
  submissionForm.value = {...initSubmissionFormData};
  submissionFormRef.value.resetFields();
}
/** Search button action */
const handleQuery = () => {
  submissionQueryParams.pageNum = 1;
  getSubmissionList();
}
/** Reset button action */
const resetQuery = () => {
  handleQuery();
}
/** Form reset */
const resetSubmissionForm = () => {
  submissionForm.value = {...initSubmissionFormData};
  submissionFormRef.value.resetFields();
}
/** Submit button */
const submitSubmissionForm = () => {
  submissionFormRef.value.validate(async (valid: boolean) => {
    if (valid && (fileUploadRef.value ? fileUploadRef.value.submitFile() : true)) {
      layoutSubmissionFormLoading.value = true;
      if (submissionForm.value.id) {
        await updateSubmission(submissionForm.value).finally(() => layoutSubmissionFormLoading.value = false);
      } else {
        await addSubmission(submissionForm.value).finally(() => layoutSubmissionFormLoading.value = false);
      }
      proxy?.$modal.msgSuccess('Lưu thành công');
      submissionDialog.visible = false;
      await getSubmissionList();
    }
  });
}
/** Save list submission seat */
const handleSubmissionTestcaseUpdate = async () => {
  layoutLoading.value = true;
  //let listSubmissionTestcaseData = submissionTestcaseList.value.filter((submissionTestcase) => submissionTestcaseIds.value.includes(parseInt(submissionTestcase.id + '')))
  let listSubmissionTestcaseData = submissionTestcaseList.value;
  await updateSubmissionTestcases(listSubmissionTestcaseData, submissionSelected.value.id).finally(() => layoutLoading.value = false);
  getSubmissionTestcaseList();
}
///////////////////////////////////////////////////////////////////////////////
// INIT SECTION
///////////////////////////////////////////////////////////////////////////////
onMounted(() => {
  getSubmissionList();
});
</script>

<template>
  <Layout3
    ref="layout"
    v-loading="layoutLoading"
    :loadingLeft="examResultLoading"
    :loadingRight="examResultDetailLoading"
    :queryParamsLeft="examResultQueryParams"
    :queryParamsRight="examResultDetailQueryParams"
    :totalLeft="examResultTotal"
    :rowDataLeft="examResultList"
    v-model:rowDataRight="examResultDetailList"
    :columnSettingLeft="examResultColumns"
    :columnSettingRight="examResultDetailColumns"
    :rowKeyLeft="examResultRowKey"
    :rowKeyRight="examResultDetailRowKey"
    :checkboxColLeft="true"
    :checkboxColRight="true"
    @onSelectedRowsLeft="handleSelectExamResultRows"
    @onSelectedRowsRight="handleSelectContainerRows"
    @onSelectCellLeft="handleSelectCellExamResult"
    @onSearchLeft="handleQuery"
    @onResetLeft="resetQuery"
    @onPagingLeft="getExamResultList"
    @onPagingRight="getExamResultDetailList"
  >
    <template v-slot:search-input>
      <el-form-item :label="$t('examResultManagement.searchInput.titleLb')" class="form-item-search">
        <el-input
          v-model="examResultQueryParams.title"
          :placeholder="$t('examResultManagement.searchInput.titlePh')"
          clearable
          style="width: 120px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
    </template>
  </Layout3>
</template>
<script setup name="examResult" lang="ts">
///////////////////////////////////////////////////////////////////////////////
// IMPORT SECTION
///////////////////////////////////////////////////////////////////////////////
// IMPORT COMPONENT
// IMPORT API
import { listExamResult, getExamResult, delExamResult, addExamResult, updateExamResult, listExamResultDetail, updateExamResultDetails } from '@/api/portCustomer/examResultManagement';
// IMPORT TYPE
import { ExamResultVO, ExamResultQuery, ExamResultDetailQuery, ExamResultForm } from '@/api/portCustomer/examResultManagement/types';
import { ElForm, FormRules } from 'element-plus';
// IMPORT GLOBAL TOOL (PROXY)
import { ComponentInternalInstance, reactive } from "vue";
const { proxy } = getCurrentInstance() as ComponentInternalInstance;
// IMPORT DICTIONARY
///////////////////////////////////////////////////////////////////////////////
// VARIABLE SECTION
///////////////////////////////////////////////////////////////////////////////
const examResultId = ref<any>('');
const examResultSelected = ref<any>({});
const layoutLoading = ref(false);
const layoutExamResultFormLoading = ref(false);
const fileUploadRef = ref();
const examResultList = ref<ExamResultVO[]>([]);
const examResultIds = ref(<any>[]);
const examResultLoading = ref(true);
const examResultTotal = ref(0);
const examResultRowKey = ref("id");
const examResultColumns = ref<GridColumn[]>([
  { prop: "examName", name: 'examResultManagement.columns.examNameLb', size: 220, readonly: true, align: 'left' },
  { prop: "userName", name: 'examResultManagement.columns.userNameLb', sortable: true, size: 160, readonly: true, align: 'left' },
  { prop: "totalScore", name: 'examResultManagement.columns.totalScoreLb', sortable: true, size: 280, readonly: true, align: 'left' },
  { prop: "completedTime", name: 'examResultManagement.columns.completedTimeLb', sortable: true, size: 160, readonly: true, formatter: 'datetime' },
]);
const examResultDetailIds = ref(<any>[]);
const examResultDetailList = ref<any[]>([]);
const examResultDetailLoading = ref(false);
const examResultDetailRowKey = ref("id");
const examResultDetailColumns = ref<GridColumn[]>([
  { prop: "problemName", name: 'examResultManagement.examResultDetailColumns.problemNameLb', sortable: true, size: 130, align: 'center' },
  { prop: "score", name: 'examResultManagement.examResultDetailColumns.scoreLb', sortable: true, size: 150, align: 'left' },
  { prop: "completedTime", name: 'examResultManagement.examResultDetailColumns.completedTimeLb', sortable: true, size: 150, align: 'left' },
]);
const examResultQueryParams = reactive<ExamResultQuery>({
  pageNum: 1,
  pageSize: 20,
  title: '',
  description: '',
  difficulty: '',
  remark: '',
  orderByColumn: 'createTime',
  isAsc: 'descending',
});
const examResultDetailQueryParams = reactive<ExamResultDetailQuery>({
  examResultId: undefined
});
const examResultFormRef = ref(ElForm);
const initExamResultFormData: ExamResultForm = {
  id: undefined,
  remark: undefined
}
const examResultDialog = reactive<DialogOption>({
  visible: false,
  title: '',
  width: '600px'
});
const examResultForm = ref<ExamResultForm>({...initExamResultFormData});
const examResultFormRules: FormRules = {
  cinemaId: [{required: true, trigger: "blur", message: "Rạp chiếu không được trống", }],
  examResultName: [{ required: true, trigger: "blur", message: "Tên phòng chiếu không được trống" }]
};
///////////////////////////////////////////////////////////////////////////////
// METHOD SECTION
///////////////////////////////////////////////////////////////////////////////
const handleSelectExamResultRows = (selectedIds: { value: (string | number)[]; }) => {
  examResultIds.value = selectedIds.value;
}
const handleSelectContainerRows = (selectedIds: { value: (string | number)[]; }) => {
  examResultDetailIds.value = selectedIds.value;
}
const handleSelectCellExamResult = (modal: any) => {
  examResultDetailIds.value = [];
  resetExamResultDetailList();
  examResultDetailQueryParams.examResultId = examResultList.value[modal.rowIndex].id;
  examResultSelected.value = examResultList.value[modal.rowIndex];
  examResultId.value = examResultList.value[modal.rowIndex].id;
  getExamResultDetailList();
}
/** Query examResult list */
const getExamResultList = async () => {
  examResultLoading.value = true;
  const res = await listExamResult(examResultQueryParams);
  examResultList.value = res.rows;
  examResultTotal.value = res.total;
  examResultLoading.value = false;
  resetExamResultDetailList();
  if (examResultList.value.length > 0) {
    examResultDetailQueryParams.examResultId = examResultList.value[0].id;
    examResultSelected.value = examResultList.value[0];
    examResultId.value = examResultList.value[0].id;
    getExamResultDetailList();
  }
}
/** Query examResult detail list */
const getExamResultDetailList = async () => {
  examResultDetailLoading.value = true;
  const res = await listExamResultDetail(examResultDetailQueryParams);
  examResultDetailList.value = res.rows;
  const diff = examResultSelected.value.rowNumber - examResultDetailList.value.length;
  if (diff > 0) {
    for (let i = 0; i < diff; i++) {
      examResultDetailList.value.push({ key: i });
    }
  }
  for (let i = 0; i < examResultDetailList.value.length; i++) {
    if (examResultDetailList.value[i].id) {
      examResultDetailList.value[i].key = examResultDetailList.value[i].id;
    }
  }
  examResultDetailLoading.value = false;
}
/** Reset container list */
const resetExamResultDetailList = () => {
  examResultSelected.value = {};
  examResultId.value = '';
  examResultDetailList.value = [];
  examResultIds.value = [];
  examResultDetailQueryParams.examResultId = undefined;
}
/** Cancel button */
const cancel = () => {
  reset();
  examResultDialog.visible = false;
}
/** Form reset */
const reset = () => {
  examResultForm.value = {...initExamResultFormData};
  examResultFormRef.value.resetFields();
}
/** Search button action */
const handleQuery = () => {
  examResultQueryParams.pageNum = 1;
  getExamResultList();
}
/** Reset button action */
const resetQuery = () => {
  handleQuery();
}
/** Form reset */
const resetExamResultForm = () => {
  examResultForm.value = {...initExamResultFormData};
  examResultFormRef.value.resetFields();
}
/** Submit button */
const submitExamResultForm = () => {
  examResultFormRef.value.validate(async (valid: boolean) => {
    if (valid && (fileUploadRef.value ? fileUploadRef.value.submitFile() : true)) {
      layoutExamResultFormLoading.value = true;
      if (examResultForm.value.id) {
        await updateExamResult(examResultForm.value).finally(() => layoutExamResultFormLoading.value = false);
      } else {
        await addExamResult(examResultForm.value).finally(() => layoutExamResultFormLoading.value = false);
      }
      proxy?.$modal.msgSuccess('Lưu thành công');
      examResultDialog.visible = false;
      await getExamResultList();
    }
  });
}
/** Save list examResult seat */
const handleExamResultDetailUpdate = async () => {
  layoutLoading.value = true;
  //let listExamResultDetailData = examResultDetailList.value.filter((examResultDetail) => examResultDetailIds.value.includes(parseInt(examResultDetail.id + '')))
  let listExamResultDetailData = examResultDetailList.value;
  await updateExamResultDetails(listExamResultDetailData, examResultSelected.value.id).finally(() => layoutLoading.value = false);
  getExamResultDetailList();
}
///////////////////////////////////////////////////////////////////////////////
// INIT SECTION
///////////////////////////////////////////////////////////////////////////////
onMounted(() => {
  getExamResultList();
});
</script>

<template>
  <Layout3
    ref="layout"
    v-loading="layoutLoading"
    :loadingLeft="examLoading"
    :loadingRight="problemLoading"
    :queryParamsLeft="examQueryParams"
    :queryParamsRight="problemQueryParams"
    :totalLeft="examTotal"
    :rowDataLeft="examList"
    v-model:rowDataRight="problemList"
    :columnSettingLeft="examColumns"
    :columnSettingRight="problemColumns"
    :rowKeyLeft="examRowKey"
    :rowKeyRight="problemRowKey"
    :checkboxColLeft="true"
    :checkboxColRight="true"
    @onSelectedRowsLeft="handleSelectExamRows"
    @onSelectedRowsRight="handleSelectContainerRows"
    @onSelectCellLeft="handleSelectCellExam"
    @onSearchLeft="handleQuery"
    @onResetLeft="resetQuery"
    @onPagingLeft="getExamList"
    @onPagingRight="getProblemList"
  >
    <template v-slot:search-input>
      <el-form-item :label="$t('examManagement.searchInput.titleLb')" class="form-item-search">
        <el-input
          v-model="examQueryParams.title"
          :placeholder="$t('examManagement.searchInput.titlePh')"
          clearable
          style="width: 120px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
    </template>
    <template v-slot:header-button-left>
      <IrButton
        colorStyle="blue"
        type="primary"
        :title="$t('examManagement.headerButton.addTt')"
        v-hasPermi="['portCustomer:examManagement:add']"
        leftIcon="plus"
        @onClick="handleExamAdd"
      />
      <IrButton
        colorStyle="gray"
        type="secondary"
        :disabledFlag="examIds.length !== 1 ? true : false"
        :title="$t('examManagement.headerButton.editTt')"
        leftIcon="edit"
        v-hasPermi="['portCustomer:examManagement:edit']"
        @onClick="handleExamUpdate"
      />
      <IrButton
        colorStyle="red"
        type="secondary"
        :disabledFlag="!!!examIds.length"
        :title="$t('examManagement.headerButton.deleteTt')"
        leftIcon="delete"
        v-hasPermi="['portCustomer:examManagement:edit']"
        @onClick="handleDeleteExam"
      />
    </template>
    <template v-slot:header-button-right>
      <IrButton
        colorStyle="green"
        type="secondary"
        title="Lưu"
        leftIcon="save"
        @onClick="handleProblemUpdate"
        :disabledFlag="!!!problemIds.length"
      />
    </template>
    <template v-slot:dialog>
      <IrDialog :dialog="examDialog">
        <template v-slot:body>
          <el-form
            ref="examFormRef"
            :rules="examFormRules"
            :model="examForm"
            label-width="150px"
            class="common-form"
            v-loading="layoutExamFormLoading"
          >
            <el-form-item label="Tên bài thi" prop="title" class="form-item-row">
              <el-input v-model="examForm.title" placeholder="Nhập" />
            </el-form-item>
            <el-form-item label="Mô tả" prop="description" class="form-item-row">
              <el-input v-model="examForm.description" placeholder="Nhập" />
            </el-form-item>
            <el-form-item label="Thời gian bắt đầu" prop="startTime" class="form-item-row">
              <el-input v-model="examForm.startTime" placeholder="Nhập" />
            </el-form-item>
            <el-form-item label="Thời gian kết thúc" prop="endTime" class="form-item-row">
              <el-input v-model="examForm.endTime" placeholder="Nhập" />
            </el-form-item>
            <el-form-item label="Ghi chú" prop="remark" class="form-item-row">
              <el-input v-model="examForm.remark" type="textarea" placeholder="Nhập" />
            </el-form-item>
          </el-form>
        </template>
        <template v-slot:footer>
          <IrButton colorStyle="gray" type="secondary" title="Đóng" :width="100" @onClick="cancel" />
          <IrButton colorStyle="blue" type="primary" :title="$t('user.dialog.footerSubmitTt')" :width="100" @onClick="submitExamForm" />
        </template>
      </IrDialog>
    </template>
  </Layout3>
</template>
<script setup name="exam" lang="ts">
///////////////////////////////////////////////////////////////////////////////
// IMPORT SECTION
///////////////////////////////////////////////////////////////////////////////
// IMPORT COMPONENT
// IMPORT API
import { listExam, getExam, delExam, addExam, updateExam, listProblem, updateProblems } from '@/api/portCustomer/examManagement';
// IMPORT TYPE
import { ExamVO, ProblemVO, ExamQuery, ProblemQuery, ExamForm } from '@/api/portCustomer/examManagement/types';
import { ElForm, FormRules } from 'element-plus';
// IMPORT GLOBAL TOOL (PROXY)
import { ComponentInternalInstance, reactive } from "vue";
const { proxy } = getCurrentInstance() as ComponentInternalInstance;
// IMPORT DICTIONARY
///////////////////////////////////////////////////////////////////////////////
// VARIABLE SECTION
///////////////////////////////////////////////////////////////////////////////
const examId = ref<any>('');
const examSelected = ref<any>({});
const layoutLoading = ref(false);
const layoutExamFormLoading = ref(false);
const fileUploadRef = ref();
const examList = ref<ExamVO[]>([]);
const examIds = ref(<any>[]);
const examLoading = ref(true);
const examTotal = ref(0);
const examRowKey = ref("id");
const examColumns = ref<GridColumn[]>([
  { prop: "title", name: 'examManagement.columns.titleLb', size: 220, readonly: true, align: 'left' },
  { prop: "description", name: 'examManagement.columns.descriptionLb', sortable: true, size: 280, readonly: true, align: 'left' },
  { prop: "startTime", name: 'examManagement.columns.startTimeLb', sortable: true, size: 160, readonly: true, align: 'center'  },
  { prop: "endTime", name: 'examManagement.columns.endTimeLb', sortable: true, size: 160, readonly: true, align: 'center'  },
  { prop: "createTime", name: 'examManagement.columns.createTimeLb', sortable: true, size: 160, readonly: true, formatter: 'datetime' },
  { prop: "remark", name: 'examManagement.columns.remarkLb', sortable: true, size: 120, readonly: true, align: 'left' },
]);
const problemIds = ref(<any>[]);
const problemList = ref<any[]>([]);
const problemLoading = ref(false);
const problemRowKey = ref("id");
const problemColumns = ref<GridColumn[]>([
  { prop: "title", name: 'examManagement.problemColumns.titleLb', sortable: true, size: 220, align: 'left' },
  { prop: "description", name: 'examManagement.problemColumns.descriptionLb', sortable: true, size: 280, align: 'left' },
  { prop: "difficulty", name: 'examManagement.problemColumns.difficultyLb', sortable: true, size: 70, align: 'left' },
  { prop: "maxScore", name: 'examManagement.problemColumns.maxScoreLb', sortable: true, size: 140, align: 'left' },
  { prop: "remark", name: 'examManagement.problemColumns.remarkLb', size: 120, align: 'left' }
]);
const examQueryParams = reactive<ExamQuery>({
  pageNum: 1,
  pageSize: 20,
  title: '',
  description: '',
  startTime: '',
  endTime: '',
  remark: '',
  orderByColumn: 'createTime',
  isAsc: 'descending',
});
const problemQueryParams = reactive<ProblemQuery>({
  examId: undefined
});
const examFormRef = ref(ElForm);
const initExamFormData: ExamForm = {
  id: undefined,
  remark: undefined
}
const examDialog = reactive<DialogOption>({
  visible: false,
  title: '',
  width: '600px'
});
const examForm = ref<ExamForm>({...initExamFormData});
const examFormRules: FormRules = {
  cinemaId: [{required: true, trigger: "blur", message: "Rạp chiếu không được trống", }],
  examName: [{ required: true, trigger: "blur", message: "Tên phòng chiếu không được trống" }]
};
///////////////////////////////////////////////////////////////////////////////
// METHOD SECTION
///////////////////////////////////////////////////////////////////////////////
const handleSelectExamRows = (selectedIds: { value: (string | number)[]; }) => {
  examIds.value = selectedIds.value;
}
const handleSelectContainerRows = (selectedIds: { value: (string | number)[]; }) => {
  problemIds.value = selectedIds.value;
}
const handleSelectCellExam = (modal: any) => {
  problemIds.value = [];
  resetProblemList();
  problemQueryParams.examId = examList.value[modal.rowIndex].id;
  examSelected.value = examList.value[modal.rowIndex];
  examId.value = examList.value[modal.rowIndex].id;
  getProblemList();
}
/** Query exam list */
const getExamList = async () => {
  examLoading.value = true;
  const res = await listExam(examQueryParams);
  examList.value = res.rows;
  examTotal.value = res.total;
  examLoading.value = false;
  resetProblemList();
  if (examList.value.length > 0) {
    problemQueryParams.examId = examList.value[0].id;
    examSelected.value = examList.value[0];
    examId.value = examList.value[0].id;
    getProblemList();
  }
}
/** Query exam detail list */
const getProblemList = async () => {
  problemLoading.value = true;
  const res = await listProblem(problemQueryParams);
  problemList.value = res.rows;
  const diff = examSelected.value.rowNumber - problemList.value.length;
  if (diff > 0) {
    for (let i = 0; i < diff; i++) {
      problemList.value.push({ key: i });
    }
  }
  for (let i = 0; i < problemList.value.length; i++) {
    if (problemList.value[i].id) {
      problemList.value[i].key = problemList.value[i].id;
    }
  }
  problemLoading.value = false;
}
/** Reset container list */
const resetProblemList = () => {
  examSelected.value = {};
  examId.value = '';
  problemList.value = [];
  examIds.value = [];
  problemQueryParams.examId = undefined;
}
/** Cancel button */
const cancel = () => {
  reset();
  examDialog.visible = false;
}
/** Form reset */
const reset = () => {
  examForm.value = {...initExamFormData};
  examFormRef.value.resetFields();
}
/** Search button action */
const handleQuery = () => {
  examQueryParams.pageNum = 1;
  getExamList();
}
/** Reset button action */
const resetQuery = () => {
  handleQuery();
}
/** Add button action */
const handleExamAdd = () => {
  examDialog.visible = true;
  examDialog.title = 'Tạo phòng chiếu';
  nextTick(() => {
    resetExamForm();
  })
}
/** Edit button action */
const handleExamUpdate = (row?: ExamForm | ExamVO) => {
  examDialog.visible = true;
  examDialog.title = 'Cập nhật thông tin bài toán';
  nextTick(async () => {
    resetExamForm();
    const _id = examIds.value[0]
    const res = await getExam(_id);
    Object.assign(examForm.value, res.data);
  })
}
/** Form reset */
const resetExamForm = () => {
  examForm.value = {...initExamFormData};
  examFormRef.value.resetFields();
}
/** Submit button */
const submitExamForm = () => {
  examFormRef.value.validate(async (valid: boolean) => {
    if (valid && (fileUploadRef.value ? fileUploadRef.value.submitFile() : true)) {
      layoutExamFormLoading.value = true;
      if (examForm.value.id) {
        await updateExam(examForm.value).finally(() => layoutExamFormLoading.value = false);
      } else {
        await addExam(examForm.value).finally(() => layoutExamFormLoading.value = false);
      }
      proxy?.$modal.msgSuccess('Lưu thành công');
      examDialog.visible = false;
      await getExamList();
    }
  });
}
/** Save list exam seat */
const handleProblemUpdate = async () => {
  layoutLoading.value = true;
  //let listProblemData = problemList.value.filter((problem) => problemIds.value.includes(parseInt(problem.id + '')))
  let listProblemData = problemList.value;
  await updateProblems(listProblemData, examSelected.value.id).finally(() => layoutLoading.value = false);
  getProblemList();
}

/** Validate delete */
const handleDeleteExam = async () => {
  const ids = examIds.value
  await proxy?.$modal.confirmDelete("Xác nhận xóa phòng?").then(action => {
    layoutLoading.value = true;
    if (action === 'cancel') return;
    delExam(ids).finally(() => layoutLoading.value = false);
  })
  getExamList();
}
///////////////////////////////////////////////////////////////////////////////
// INIT SECTION
///////////////////////////////////////////////////////////////////////////////
onMounted(() => {
  getExamList();
});
</script>

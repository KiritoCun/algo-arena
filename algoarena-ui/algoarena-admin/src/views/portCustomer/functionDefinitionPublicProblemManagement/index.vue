<template>
  <Layout3
    ref="layout"
    v-loading="layoutLoading"
    :loadingLeft="problemLoading"
    :loadingRight="functionDefinitionLoading"
    :queryParamsLeft="problemQueryParams"
    :queryParamsRight="functionDefinitionQueryParams"
    :totalLeft="problemTotal"
    :rowDataLeft="problemList"
    v-model:rowDataRight="functionDefinitionList"
    :columnSettingLeft="problemColumns"
    :columnSettingRight="functionDefinitionColumns"
    :rowKeyLeft="problemRowKey"
    :rowKeyRight="functionDefinitionRowKey"
    :checkboxColLeft="true"
    :checkboxColRight="true"
    @onSelectedRowsLeft="handleSelectProblemRows"
    @onSelectedRowsRight="handleSelectContainerRows"
    @onSelectCellLeft="handleSelectCellProblem"
    @onSearchLeft="handleQuery"
    @onResetLeft="resetQuery"
    @onPagingLeft="getProblemList"
    @onPagingRight="getFunctionDefinitionList"
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
        leftIcon="plus"
        @onClick="handleProblemAdd"
      />
      <IrButton
        colorStyle="gray"
        type="secondary"
        :disabledFlag="problemIds.length !== 1 ? true : false"
        :title="$t('problemManagement.headerButton.editTt')"
        leftIcon="edit"
        @onClick="handleProblemUpdate"
      />
      <IrButton
        colorStyle="red"
        type="secondary"
        :disabledFlag="!!!problemIds.length"
        :title="$t('problemManagement.headerButton.deleteTt')"
        leftIcon="delete"
        @onClick="handleDeleteProblem"
      />
    </template>
    <template v-slot:header-button-right>
      <IrButton
        colorStyle="green"
        type="secondary"
        :title="$t('problemManagement.headerButton.saveTt')"
        leftIcon="save"
        @onClick="handleFunctionDefinitionUpdate"
        :disabledFlag="!!!functionDefinitionIds.length"
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
              <el-input v-model="problemForm.description" type="textarea" placeholder="Nhập" />
            </el-form-item>
            <el-form-item label="Độ khó" prop="difficulty" class="form-item-row">
              <el-select v-model="problemForm.difficulty" placeholder="Độ khó">
                <el-option v-for="dict in level" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="Số testcase" prop="numberTestcase" class="form-item-row">
              <el-input v-model="problemForm.numberTestcase" type="number" placeholder="Nhập" />
            </el-form-item>
            <el-form-item label="Khóa đường dẫn" prop="keyPath" class="form-item-row">
              <el-input v-model="problemForm.keyPath" placeholder="Nhập" />
            </el-form-item>
            <el-form-item label="Phân loại" prop="category" class="form-item-row">
              <el-input v-model="problemForm.category" placeholder="Nhập" />
            </el-form-item>
            <el-form-item label="Mã video lời giải" prop="videoId" class="form-item-row">
              <el-input v-model="problemForm.videoId" placeholder="Nhập" />
            </el-form-item>
            <el-form-item label="Định nghĩa hàm java" prop="functionDefinitionJava" class="form-item-row">
              <el-input v-model="problemForm.functionDefinitionJava" placeholder="VD: public int[] twoSum(int[] nums, int target)" />
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
    </template>
  </Layout3>
</template>
<script setup name="problem" lang="ts">
///////////////////////////////////////////////////////////////////////////////
// IMPORT SECTION
///////////////////////////////////////////////////////////////////////////////
// IMPORT COMPONENT
// IMPORT API
import { listProblem, getProblem, delProblem, addProblem, updateProblem, updateFunctionDefinition, listFunctionDefinition } from '@/api/portCustomer/publicProblemManagement';
// IMPORT TYPE
import { ProblemVO, FunctionDefinitionVO, ProblemQuery, FunctionDefinitionQuery, ProblemForm, FunctionDefinitionForm } from '@/api/portCustomer/publicProblemManagement/types';
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
const problemId = ref<any>('');
const problemSelected = ref<any>({});
const layoutLoading = ref(false);
const layoutProblemFormLoading = ref(false);
const fileUploadRef = ref();
const problemList = ref<ProblemVO[]>([]);
const problemIds = ref(<any>[]);
const problemLoading = ref(true);
const problemTotal = ref(0);
const problemRowKey = ref("id");
const problemColumns = ref<GridColumn[]>([
  { prop: "title", name: 'problemManagement.columns.titleLb', size: 220, readonly: true, align: 'left' },
  { prop: "difficulty", name: 'problemManagement.columns.difficultyLb', sortable: true, size: 90, readonly: true, align: 'left', dictData: level  },
  { prop: "keyPath", name: 'problemManagement.columns.keyPathLb', sortable: true, size: 120, readonly: true, align: 'left' },
  { prop: "category", name: 'problemManagement.columns.categoryLb', sortable: true, size: 150, readonly: true, align: 'left' },
  { prop: "videoId", name: 'problemManagement.columns.videoIdLb', sortable: true, size: 120, readonly: true, align: 'left' },
  { prop: "createTime", name: 'problemManagement.columns.createTimeLb', sortable: true, size: 160, readonly: true, formatter: 'datetime' },
  { prop: "remark", name: 'problemManagement.columns.remarkLb', sortable: true, size: 120, readonly: true, align: 'left' },
]);
const functionDefinitionIds = ref(<any>[]);
const functionDefinitionList = ref<any[]>([]);
const functionDefinitionLoading = ref(false);
const functionDefinitionRowKey = ref("id");
const functionDefinitionColumns = ref<GridColumn[]>([
  { prop: "language", name: 'problemManagement.functionDefinitionColumns.languageLb', sortable: true, size: 140, align: 'left' },
  { prop: "functionSignature", name: 'problemManagement.functionDefinitionColumns.functionDefinitionLb', sortable: true, size: 420, align: 'left' },
  { prop: "remark", name: 'problemManagement.functionDefinitionColumns.remarkLb', size: 120, align: 'left' }
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
const functionDefinitionQueryParams = reactive<FunctionDefinitionQuery>({
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
  difficulty: [{ required: true, trigger: "blur", message: "Độ khó là bắt buộc" }],
  numberTestcase: [{ required: true, trigger: "blur", message: "Số lượng testcase là bắt buộc" }],
  keyPath: [{ required: true, trigger: "blur", message: "Khóa đường dẫn là bắt buộc" }],
  functionDefinitionJava: [{ required: true, trigger: "blur", message: "Định nghĩa hàm java là bắt buộc" }]
};

///////////////////////////////////////////////////////////////////////////////
// METHOD SECTION
///////////////////////////////////////////////////////////////////////////////
const handleSelectProblemRows = (selectedIds: { value: (string | number)[]; }) => {
  problemIds.value = selectedIds.value;
}
const handleSelectContainerRows = (selectedIds: { value: (string | number)[]; }) => {
  functionDefinitionIds.value = selectedIds.value;
}
const handleSelectCellProblem = (modal: any) => {
  functionDefinitionIds.value = [];
  resetFunctionDefinitionList();
  functionDefinitionQueryParams.problemId = problemList.value[modal.rowIndex].id;
  problemSelected.value = problemList.value[modal.rowIndex];
  problemId.value = problemList.value[modal.rowIndex].id;
  getFunctionDefinitionList();
}
/** Query problem list */
const getProblemList = async () => {
  problemLoading.value = true;
  const res = await listProblem(problemQueryParams);
  problemList.value = res.rows;
  problemTotal.value = res.total;
  problemLoading.value = false;
  resetFunctionDefinitionList();
  if (problemList.value.length > 0) {
    functionDefinitionQueryParams.problemId = problemList.value[0].id;
    problemSelected.value = problemList.value[0];
    problemId.value = problemList.value[0].id;
    getFunctionDefinitionList();
  }
}
/** Query problem detail list */
const getFunctionDefinitionList = async () => {
  functionDefinitionLoading.value = true;
  const res = await listFunctionDefinition(functionDefinitionQueryParams);
  functionDefinitionList.value = res.rows;
  const diff = problemSelected.value.rowNumber - functionDefinitionList.value.length;
  if (diff > 0) {
    for (let i = 0; i < diff; i++) {
      functionDefinitionList.value.push({ key: i });
    }
  }
  for (let i = 0; i < functionDefinitionList.value.length; i++) {
    if (functionDefinitionList.value[i].id) {
      functionDefinitionList.value[i].key = functionDefinitionList.value[i].id;
    }
  }
  functionDefinitionLoading.value = false;
}
/** Reset container list */
const resetFunctionDefinitionList = () => {
  problemSelected.value = {};
  problemId.value = '';
  functionDefinitionList.value = [];
  problemIds.value = [];
  functionDefinitionQueryParams.problemId = undefined;
}
/** Cancel button */
const cancel = () => {
  reset();
  problemDialog.visible = false;
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
/** Save list functionDefinition */
const handleFunctionDefinitionUpdate = async () => {
  layoutLoading.value = true;
  //let listFunctionDefinitionData = functionDefinitionList.value.filter((functionDefinition) => functionDefinitionIds.value.includes(parseInt(functionDefinition.id + '')))
  let listFunctionDefinitionData = functionDefinitionList.value;
  await updateFunctionDefinition(listFunctionDefinitionData, problemSelected.value.id).finally(() => layoutLoading.value = false);
  getFunctionDefinitionList();
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

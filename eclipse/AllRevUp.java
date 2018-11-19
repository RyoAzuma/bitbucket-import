package odasan1;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.util.MessageBox;

public class AllRevUp extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		// TODO Auto-generated method stub
		
		//TC�̃Z�b�V�������擾
		AbstractAIFUIApplication app = AIFUtility.getActiveDesktop().getCurrentApplication();
		InterfaceAIFComponent comp[] = app.getTargetComponents();
		
		for(int i = 0; i < comp.length; i++)
		{
			//IR�ȊO��I�������ꍇ(IF)
			if(!comp[i].getType().equals("Item"))
			{
				MessageBox.post("Item�ȊO�I�����Ȃ��ł�������", "Warning", MessageBox.ERROR);
				return null;
			}
			else
			{				
				try {
					
					TCComponentItem item = (TCComponentItem) comp[i];
					AIFComponentContext[] context = item.getChildren("revision_list");
					
					//�D�c����Q�l�\�[�X Add
//					AIFComponentContext[] contextAll = item.getChildren();
//					for (int cnt =0 ; cnt  < contextAll.length; cnt++)
//					{				
//						TCComponent comp2 = (TCComponent)contextAll[cnt].getComponent();
//						if(comp2.getType().equals("TCComponentItemRevision")){	
//						
//						}
//
//						TCComponentItemRevision irs2 = (TCComponentItemRevision) context[cnt].getComponent();
//						irs2.setProperty("object_desc", "old ItemRevsion");
//					}
					//�D�c����Q�l�\�[�X end
					
					//���r�W�����A�b�v�������s
					TCComponentItemRevision irs = item.getLatestItemRevision();
					irs.saveAs(null);
					
					//�������͏������s
					for (int cnt =0 ; cnt  < context.length; cnt++)
					{					
						TCComponentItemRevision irs2 = (TCComponentItemRevision) context[cnt].getComponent();
						irs2.setProperty("object_desc", "old ItemRevsion");
					}
					
					
					//catch����
				} catch (TCException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//�����I��
		return null;
	}

}

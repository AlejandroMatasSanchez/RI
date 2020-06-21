package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoiceDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.cws.domain.WorkOrder.WorkOrderStatus;

public class CreateInvoiceFor implements Command<InvoiceDto> {

	private List<String> workOrderIds;
	private InvoiceRepository repoI = Factory.repository.forInvoice();
	private WorkOrderRepository repoW = Factory.repository.forWorkOrder();

	public CreateInvoiceFor(List<String> workOrderIds) {
		this.workOrderIds = workOrderIds;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {		
		//get a the list of workOrders
		List<WorkOrder> workOrders = repoW.findByIds(workOrderIds);
		//get the next number
		long number = repoI.getNextInvoiceNumber();
		//Check the workOrders
		checkWorkOrders(workOrders);
		//Create the Invoice
		Invoice i = new Invoice(number, workOrders);

		repoI.add(i);

		return DtoAssembler.toDto(i);
	}

	private void checkWorkOrders(List<WorkOrder> workOrders) {
		for (WorkOrder workOrder : workOrders) {
			if(workOrder.getStatus() != WorkOrderStatus.FINISHED) {
				throw new IllegalStateException("The workorder is not finished");
			}
		}
	}

}

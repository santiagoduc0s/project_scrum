var dragContainer = document.querySelector('.drag-container');
var itemContainers = [].slice.call(document.querySelectorAll('.board-column-content'));
var columnGrids = [];
var boardGrid;
var itemColumn;

var backlogCol;
var todoCol;
var workingCol;
var doneCole;

// Init the column grids so we can drag those items around.
itemContainers.forEach(function (container, index) {
    var grid = new Muuri(container, {
        items: '.board-item',
        dragEnabled: true,
        dragStartPredicate: {
            distance: 1,
            delay: 1
        },
        dragSort: function () {
            return columnGrids;
        },
        dragContainer: dragContainer,
        dragAutoScroll: {
            targets: (item) => {
                return [{
                    element: window,
                    priority: 0
                },
                    {
                        element: item.getGrid().getElement().parentNode,
                        priority: 1
                    },
                ];
            }
        },
    }).on('dragInit', function (item) {
        item.getElement().style.width = item.getWidth() + 'px';
        item.getElement().style.height = item.getHeight() + 'px';
    }).on('dragReleaseEnd', function (item) {
        item.getElement().style.width = '';
        item.getElement().style.height = '';
        item.getGrid().refreshItems([item]);
    }).on('layoutStart', function () {
        boardGrid.refreshItems().layout();
    }).on('dragEnd', function (item, event) {
        console.log(item.getGrid()._element.id);
        console.log(item._element.childNodes[1].id);
        changeTaskColumn(item._element.childNodes[1].id, item.getGrid()._element.id)
    });

    columnGrids.push(grid);
});


// Init board grid so we can drag those columns around.
boardGrid = new Muuri('.board', {
    // dragEnabled: true, // drag columns
    dragHandle: '.board-column-header'
});

function changeTaskColumn(taskId, column) {
    console.log(window.location.origin + '/cambiar-tarea-de-columna');


    $.ajax({
        type: "GET",
        url: window.location.origin + '/cambiar-tarea-de-columna?taskId='+taskId,
        // data: {
        //     taskId: JSON.stringify(parseInt(taskId))
        // },
        success: function (response) {
            console.log('gud');
            console.log(response);
        },
        error: function (error) {
            console.log('error');
            console.log(error)
        }
    });
}
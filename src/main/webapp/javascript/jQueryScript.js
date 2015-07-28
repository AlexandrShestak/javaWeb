
$(document).ready(
    $(document).on("click", $('.deleteComment'), functionDelete())
);
$(document).ready(
    $(document).on("click", $('.editComment'), editComment())
);
/*ajax method to add comments*/
$(document).ready(function(){
    $('#addComment').click(function(){
        $.ajax({
            url: 'comments',
            type: "POST",
            data: {
                newsId:$('#newsIdToAjax').val(),
                action:$('#actionToAjax').val(),
                commentText:$('#commentTextToAjax').val()
            },
            success: function (response) {
                obj =  response;
                str1 = $('#commentsTable').html()
                str2 = str1+'<tr><td colspan="5"><table width="80%"><tr><td colspan="5"><textarea class="comments" name="commentText" readonly="readonly" >'+$('#commentTextToAjax').val()+'</textarea></td></tr><tr><td align="left">'+obj.creationDate+'</td><td align="right">'+obj.commentatorUsername+'</td><td><input type="hidden" name="action" value="edit"><input type="hidden" name="commentId" id="commentId" value='+obj.commentId+'><button class="editComment">'+$('#editAjax').text()+'</button></td><td><input type="hidden" name="action" value="delete"><input type="hidden" name="commentId" id="commentId" value='+obj.commentId+'><button  class="deleteComment">'+$('#deleteAjax').text()+'</button></td></tr><tr><td colspan="5"><hr align="right" width="40%" size="3" color="#0000dd" /> </td> </tr>  </table> </td> </tr>'
                /*     $('#commentsTable')
                 .append('<tr><td colspan="5"><table width="80%"><tr><td colspan="5"><textarea class="comments" name="commentText" readonly="readonly" >'+$('#commentTextToAjax').val()+'</textarea></td></tr><tr><td align="left">'+obj.creationDate+'</td><td align="right">'+obj.commentatorUsername+'</td><td><input type="hidden" name="action" value="edit"><input type="hidden" name="commentId" id="commentId" value='+obj.commentId+'><button class="editComment">'+$('#editAjax').text()+'</button></td><td><input type="hidden" name="action" value="delete"><input type="hidden" name="commentId" id="commentId" value='+obj.commentId+'><button  class="deleteComment">'+$('#deleteAjax').text()+'</button></td></tr><tr><td colspan="5"><hr align="right" width="40%" size="3" color="#0000dd" /> </td> </tr>  </table> </td> </tr>')
                 */
                $('#commentsTable').html(str2);
                $(document).ready(
                    $(document).on("click", $('.deleteComment'), functionDelete())
                );
                $(document).ready(
                    $(document).on("click", $('.editComment'), editComment())
                );
                /* $('#commentsTable')
                 .append('<tr><td align="left">'+obj.creationDate+'</td><td align="right">'+obj.commentatorUsername+'</td><td><input type="hidden" name="action" value="edit"><input type="hidden" name="commentId" value='+obj.commentId+'><button type="submit">'+$('#editAjax').text()+'</button></td><td><input type="hidden" name="action" value="delete"><input type="hidden" name="commentId" value='+obj.commentId+'><button type="submit">'+$('#deleteAjax').text()+'</button></td></tr>')
                 *//*    $('#commentsTable').
                 append('<td><form action="/comments" method="get"><input type="hidden" name="action" value="edit"><input type="hidden" name="commentId" value='+obj.commentId+'/><button type="submit"><fmt:message key="edit"/></button></form></td>')
                 $('#commentsTable').
                 append('<td><form action="/comments" method="get"><input type="hidden" name="action" value="delete"><input type="hidden" name="commentId" value='+obj.commentId+'/><button type="submit"><fmt:message key="edit"/></button></form></td></tr>')
                 *//*               $('#commentsTable').
                 append('<tr><td colspan="5"><hr align="right" width="40%" size="3" color="#0000dd" /> </td> </tr>  </table> </td> </tr>')
                 */
                /*    $(document).ready(
                 $(document).off("click",$('.deleteComment'),functionDelete())

                 );
                 $(document).ready(
                 $(document).on("click", $('.deleteComment'), functionDelete())

                 );*/
            }
        });

    })
});

/*ajax method to delete comment*/
function functionDelete()
{
    $(document).ready(function() {
        $('.deleteComment').click(function () {
            commentId = $(this).parent().children("input[name='commentId']").val()
            str = '#' + commentId
            row = $(this).parent().parent().parent()
            $.ajax({
                url: 'comments',
                type: "POST",
                data: {
                    action: 'delete',
                    commentId: commentId
                },
                success: function (response) {
                    row.remove()

                }
            });

        })
    })
}

/*$(document).ready(function() {
 $('.deleteComment').click(function () {
 commentId = $(this).parent().children("input[id='commentId']").val()
 str = '#' + commentId
 row = $(this).parent().parent().parent()
 $.ajax({
 url: 'comments',
 type: "POST",
 data: {
 action: 'delete',
 commentId: commentId
 },
 success: function (response) {
 row.remove()

 }
 });

 })
 });*/


/*ajax method to edit comments*/
function editComment() {
    $(document).ready(function () {
        $('.editComment').click(function () {
            commentId = $(this).parent().children("input[name='commentId']").val()
            var textAreaCommentText = $(this).parent().parent().parent().children().children().children("textarea[name='commentText']")
            if(textAreaCommentText.is('[readonly]')){
                textAreaCommentText.removeAttr("readonly")
            }else{
                $.ajax({
                    url: 'comments',
                    type: "POST",
                    data: {
                        action: 'edit',
                        commentId: commentId,
                        commentText: textAreaCommentText.text()
                    },
                    success: function (response) {
                        textAreaCommentText.attr('readonly','readonly');
                    }
                });
            }
        })
    })
}


$(document).ready(function(){
    $('textarea').autoResize()
});


(function($){

    $.fn.autoResize = function(options) {

        // Just some abstracted details,
        // to make plugin users happy:
        var settings = $.extend({
            onResize : function(){},
            animate : true,
            animateDuration : 150,
            animateCallback : function(){},
            extraSpace : 20,
            limit: 500
        }, options);

        // Only textarea's auto-resize:
        this.filter('textarea').each(function(){

            // Get rid of scrollbars and disable WebKit resizing:
            var textarea = $(this).css({resize:'none','overflow-y':'hidden'}),

            // Cache original height, for use later:
                origHeight = textarea.height(),

            // Need clone of textarea, hidden off screen:
                clone = (function(){

                    // Properties which may effect space taken up by chracters:
                    var props = ['height','width','lineHeight','textDecoration','letterSpacing'],
                        propOb = {};

                    // Create object of styles to apply:
                    $.each(props, function(i, prop){
                        propOb[prop] = textarea.css(prop);
                    });

                    // Clone the actual textarea removing unique properties
                    // and insert before original textarea:
                    return textarea.clone().removeAttr('id').removeAttr('name').css({
                        position: 'absolute',
                        top: 0,
                        left: -9999
                    }).css(propOb).attr('tabIndex','-1').insertBefore(textarea);

                })(),
                lastScrollTop = null,
                updateSize = function() {

                    // Prepare the clone:
                    clone.height(0).val($(this).val()).scrollTop(10000);

                    // Find the height of text:
                    var scrollTop = Math.max(clone.scrollTop(), origHeight) + settings.extraSpace,
                        toChange = $(this).add(clone);

                    // Don't do anything if scrollTip hasen't changed:
                    if (lastScrollTop === scrollTop) { return; }
                    lastScrollTop = scrollTop;

                    // Check for limit:
                    if ( scrollTop >= settings.limit ) {
                        // [NEW ADDED]
                        // Do NOT limit height when limit = 0
                        if ( settings.limit != 0) {
                            // Applied limit height
                            $(this).css('overflow-y','').css('height', settings.limit+'px');
                            return;
                        }
                    }
                    // Fire off callback:
                    settings.onResize.call(this);

                    // Either animate or directly apply height:
                    settings.animate && textarea.css('display') === 'block' ?
                        toChange.stop().animate({height:scrollTop}, settings.animateDuration, settings.animateCallback)
                        : toChange.height(scrollTop);
                };

            // Bind namespaced handlers to appropriate events:
            textarea
                .unbind('.dynSiz')
                .bind('keyup.dynSiz', updateSize)
                .bind('keydown.dynSiz', updateSize)
                .bind('change.dynSiz', updateSize);

        });

        // Chain:
        return this;

    };



})(jQuery);

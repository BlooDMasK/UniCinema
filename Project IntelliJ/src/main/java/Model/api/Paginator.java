package Model.api;

public class Paginator {
    private final int limit, offset;


    public Paginator(int page, int itemsPerPage) {
        this.limit = itemsPerPage;
        if(page == 1)
            this.offset = 0;
        else
            this.offset = (page - 1) * itemsPerPage + 1;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getPages(int size) {
        int additionalPage;
        if(size % limit == 0)
            additionalPage = 0;
        else
            additionalPage = 1;

        return (size / limit) + additionalPage;
    }
}

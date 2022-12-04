import { ref, Ref } from "@vue/reactivity";
import { listArticles, feedArticle } from "@/api/index";

import { usePagination } from "@/ts/usePagination";


export interface ArticleList {
    slug: string,
    title: string,
    description: string,
    favorited: boolean,
    favoritesCount: number,
    createdAt: string,
    author: {
        username: string,
        image: string
    }
}

export function usePaginationApi(
    currentPage: Ref<number>,
    rowsPerPage?: Ref<number>
) {
    const articleLists: Ref<ArticleList[]> = ref([]);

    const listsAreLoading = ref(false);
    const isEmpty = ref(false);

    const { paginatedArray, numberOfPages } = usePagination<ArticleList>({
        rowsPerPage,
        arrayToPaginate: articleLists,
        currentPage
    });

    const feedLists = async () => {
        listsAreLoading.value = true;
        isEmpty.value = false;
        try{
            const { data } = await feedArticle();
            articleLists.value = data.articles;
            if(data.articlesCount == 0){
                isEmpty.value = true;
            }
        } catch (err) {
            console.log(err);
        } finally {
            listsAreLoading.value = false;
        }
    }

    const loadLists = async () => {
        listsAreLoading.value = true;
        isEmpty.value = false;
        try {
            const { data } = await listArticles();
            articleLists.value = data.articles;
            if(data.articlesCount == 0){
                isEmpty.value = true;
            }
        } catch (err) {
            console.log(err);
        } finally {
            listsAreLoading.value = false;
        }
    };

    return {
        articleLists: paginatedArray,
        loadLists,
        feedLists,
        listsAreLoading,
        isEmpty,
        numberOfPages
    };
}